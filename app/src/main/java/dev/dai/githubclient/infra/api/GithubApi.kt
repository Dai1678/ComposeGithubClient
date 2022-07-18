package dev.dai.githubclient.infra.api

import dev.dai.githubclient.infra.api.body.UserSearchResultIndexBody
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
  @GET("/search/users")
  suspend fun searchUsers(
    @Query("q") query: String
  ): UserSearchResultIndexBody
}
