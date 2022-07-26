package dev.dai.githubclient.data.api.body

import com.google.gson.annotations.SerializedName

data class UserBody(
  val id: Int,
  @SerializedName("login") val userName: String,
  @SerializedName("name") val fullName: String,
  val avatarUrl: String,
  val followers: Int,
  val following: Int
)
