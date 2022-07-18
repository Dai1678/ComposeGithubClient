package dev.dai.githubclient.domain.mapper

import dev.dai.githubclient.domain.model.UserSearchResult
import dev.dai.githubclient.domain.model.UserSearchResultIndex
import dev.dai.githubclient.infra.api.body.UserSearchResultBody
import dev.dai.githubclient.infra.api.body.UserSearchResultIndexBody

fun UserSearchResultIndexBody.toUserSearchResultIndex(): UserSearchResultIndex {
  return UserSearchResultIndex(
    totalCount = totalCount,
    userList = items.map { it.toUserSearchResult() }
  )
}

private fun UserSearchResultBody.toUserSearchResult(): UserSearchResult {
  return UserSearchResult(
    id = id,
    userName = userName,
    avatarUrl = avatarUrl
  )
}
