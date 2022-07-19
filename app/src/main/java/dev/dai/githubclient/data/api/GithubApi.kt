package dev.dai.githubclient.data.api

import dev.dai.githubclient.data.api.body.UserSearchResultIndexBody
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
  @GET("/search/users")
  suspend fun searchUsers(
    @Query("q") query: String
  ): UserSearchResultIndexBody
}
