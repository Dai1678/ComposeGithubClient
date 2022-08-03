package dev.dai.githubclient.model

data class GithubRepo(
  val id: Int,
  val title: String,
  val description: String?,
  val fork: Boolean,
  val url: String,
  val language: String?,
  val stargazersCount: Int
)
