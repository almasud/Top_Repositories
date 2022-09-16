package com.github.almasud.top_repositories.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("id")
    val ownerId: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val ownerUrl: String
) : Parcelable