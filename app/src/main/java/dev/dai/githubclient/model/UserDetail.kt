package dev.dai.githubclient.model

data class UserDetail(
  val user: User,
  val githubRepoList: List<GithubRepo>
)
