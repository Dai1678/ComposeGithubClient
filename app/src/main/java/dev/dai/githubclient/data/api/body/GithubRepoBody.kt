package dev.dai.githubclient.data.api.body

data class GithubRepoIndexBody(
  val items: List<GithubRepoBody>
)

data class GithubRepoBody(
  val id: Int,
  val title: String,
  val description: String?,
  val fork: Boolean,
  val url: String,
  val language: String?,
  val stargazerCount: Int
)
