package dev.dai.githubclient.model

data class GithubRepo(
  val title: String,
  val description: String,
  val form: Boolean,
  val url: String,
  val language: String,
  val stargazerCount: Int
)
