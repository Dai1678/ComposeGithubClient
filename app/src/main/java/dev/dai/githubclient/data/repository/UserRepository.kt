package dev.dai.githubclient.data.repository

import dev.dai.githubclient.data.api.GithubApi
import dev.dai.githubclient.data.mapper.toGithubRepoList
import dev.dai.githubclient.data.mapper.toUser
import dev.dai.githubclient.model.UserDetail

interface UserRepository {
  suspend fun userDetail(userName: String): UserDetail
}

class DefaultUserRepository(private val githubApi: GithubApi) : UserRepository {
  override suspend fun userDetail(userName: String): UserDetail {
    val user = githubApi.user(userName).toUser()
    val githubRepoList =
      githubApi.userGithubRepo(userName = userName, perPage = FETCH_GITHUB_REPO_PER_PAGE)
        .toGithubRepoList()
    return UserDetail(
      user = user,
      githubRepoList = githubRepoList
    )
  }

  companion object {
    private const val FETCH_GITHUB_REPO_PER_PAGE = 50
  }
}
