package dev.dai.githubclient.data.api

import dev.dai.githubclient.data.api.body.GithubRepoBody
import dev.dai.githubclient.data.api.body.UserBody
import dev.dai.githubclient.data.api.body.UserSearchResultIndexBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
  @GET("/search/users")
  suspend fun searchUsers(
    @Query("q") query: String
  ): UserSearchResultIndexBody

  @GET("/users/{username}")
  suspend fun user(
    @Path("username") userName: String
  ): UserBody

  @GET("/users/{username}/repos")
  suspend fun userGithubRepo(
    @Path("username") userName: String,
    @Query("per_page") perPage: Int
  ): List<GithubRepoBody>
}
