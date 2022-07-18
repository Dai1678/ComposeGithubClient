package dev.dai.githubclient.domain.repository

import dev.dai.githubclient.domain.mapper.toUserSearchResultIndex
import dev.dai.githubclient.domain.model.UserSearchResultIndex
import dev.dai.githubclient.infra.api.GithubApi
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
