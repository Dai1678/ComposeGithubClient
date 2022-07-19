package dev.dai.githubclient.data.mapper

import dev.dai.githubclient.data.api.body.UserSearchResultBody
import dev.dai.githubclient.data.api.body.UserSearchResultIndexBody
import dev.dai.githubclient.model.UserSearchResult
import dev.dai.githubclient.model.UserSearchResultIndex

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
