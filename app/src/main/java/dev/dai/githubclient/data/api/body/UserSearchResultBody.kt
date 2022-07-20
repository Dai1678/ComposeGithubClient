package dev.dai.githubclient.data.api.body

import com.google.gson.annotations.SerializedName

data class UserSearchResultIndexBody(
  val totalCount: Int,
  val items: List<UserSearchResultBody>
)

data class UserSearchResultBody(
  val id: Int,
  @SerializedName("login") val userName: String,
  val avatarUrl: String
)
