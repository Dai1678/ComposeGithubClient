package dev.dai.githubclient.data.api.body

data class GithubRepoBody(
  val id: Int,
  val name: String,
  val description: String?,
  val fork: Boolean,
  val htmlUrl: String,
  val language: String?,
  val stargazersCount: Int
)
