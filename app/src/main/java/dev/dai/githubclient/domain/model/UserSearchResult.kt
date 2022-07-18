package dev.dai.githubclient.domain.model

data class UserSearchResultIndex(
  val totalCount: Int,
  val userList: List<UserSearchResult>
)

data class UserSearchResult(
  val id: Int,
  val userName: String,
  val avatarUrl: String
)
