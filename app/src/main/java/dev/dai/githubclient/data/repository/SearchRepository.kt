package dev.dai.githubclient.data.repository

import dev.dai.githubclient.data.api.GithubApi
import dev.dai.githubclient.data.mapper.toUserSearchResultIndex
import dev.dai.githubclient.model.UserSearchResultIndex
import javax.inject.Inject

interface SearchRepository {
  suspend fun searchUser(userName: String): UserSearchResultIndex
}

class DefaultSearchRepository @Inject constructor(
  private val githubApi: GithubApi
) : SearchRepository {
  override suspend fun searchUser(userName: String): UserSearchResultIndex {
    return githubApi.searchUsers(userName).toUserSearchResultIndex()
  }
}
