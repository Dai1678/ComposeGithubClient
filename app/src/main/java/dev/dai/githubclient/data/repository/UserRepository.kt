package dev.dai.githubclient.data.repository

import dev.dai.githubclient.data.api.GithubApi
import dev.dai.githubclient.data.mapper.toGithubRepoList
import dev.dai.githubclient.data.mapper.toUser
import dev.dai.githubclient.model.UserDetail
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
  suspend fun userDetail(userName: String): UserDetail
}

@Singleton
class DefaultUserRepository @Inject constructor(
  private val githubApi: GithubApi
) : UserRepository {

  override suspend fun userDetail(userName: String): UserDetail {
    val user = githubApi.user(userName).toUser()
    val githubRepoList =
      githubApi.userGithubRepo(
        userName = userName,
        perPage = FETCH_GITHUB_REPO_PER_PAGE,
        sort = FETCH_GITHUB_REPO_SORT
      ).toGithubRepoList()
    return UserDetail(
      user = user,
      githubRepoList = githubRepoList
    )
  }

  companion object {
    private const val FETCH_GITHUB_REPO_PER_PAGE = 50
    private const val FETCH_GITHUB_REPO_SORT = "pushed"
  }
}
