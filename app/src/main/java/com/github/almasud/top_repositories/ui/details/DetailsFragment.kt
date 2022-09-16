package com.github.almasud.top_repositories.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.almasud.top_repositories.databinding.FragmentDetailsBinding
import com.github.almasud.top_repositories.model.Repo
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val viewModel by viewModels<DetailsViewModel>()

    // The properties are only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        // Load the model as arguments value
        val repo = DetailsFragmentArgs.fromBundle(
            requireArguments()
        ).repoObject

        bind(repo)

        return binding.root
    }

    private fun bind(repo: Repo) {
        Glide.with(requireContext())
            .load(repo.owner.avatarUrl)
            .into(binding.circularImageProfile)
        binding.tvName.text = repo.owner.login
        binding.tvAbout.text = repo.description

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("MM-dd-yy hh:mm:ss a")
        val parsedDate: Date? = repo.updated_at?.let { inputFormat.parse(it) }
        val formattedDate: String? = parsedDate?.let { outputFormat.format(it) }
        binding.tvDate.text = formattedDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }

}