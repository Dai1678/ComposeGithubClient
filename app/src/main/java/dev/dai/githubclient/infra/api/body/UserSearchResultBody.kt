package dev.dai.githubclient.infra.api.body

import com.google.gson.annotations.SerializedName

data class UserSearchResultIndexBody(
  val totalCount: Int,
  val items: List<UserSearchResultBody>
)

data class UserSearchResultBody(
  @SerializedName("login") val userName: String,
  val avatarUrl: String
)
