package dev.dai.githubclient.domain.repository

import dev.dai.githubclient.domain.model.UserSearchResultIndex

interface SearchRepository {
  suspend fun searchUser(): UserSearchResultIndex
}

class DefaultSearchRepository : SearchRepository {
  override suspend fun searchUser(): UserSearchResultIndex {
    TODO("Not yet implemented")
  }
}
