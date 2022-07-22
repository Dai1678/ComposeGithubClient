package dev.dai.githubclient

import dev.dai.githubclient.data.api.GithubApi
import dev.dai.githubclient.data.api.body.UserSearchResultBody
import dev.dai.githubclient.data.api.body.UserSearchResultIndexBody
import dev.dai.githubclient.data.repository.DefaultSearchRepository
import dev.dai.githubclient.model.UserSearchResult
import dev.dai.githubclient.model.UserSearchResultIndex
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchRepositorySpec : DescribeSpec({

  val githubApi = mockk<GithubApi>()

  beforeSpec {
    coroutineTestScope = true
  }

  val userSearchResultIndexBody = UserSearchResultIndexBody(
    totalCount = 1,
    items = listOf(
      UserSearchResultBody(
        id = 0,
        userName = "user",
        avatarUrl = "https://placehold.jp/240x240.png"
      )
    )
  )

  describe("#searchUser") {
    val query = "user"
    context("response success") {
      coEvery {
        githubApi.searchUsers(query = query)
      } returns userSearchResultIndexBody
      val repository = DefaultSearchRepository(githubApi)
      val response = repository.searchUser(query)

      it("response should be UserSearchResultIndex") {
        response shouldBe UserSearchResultIndex(
          totalCount = 1,
          userList = listOf(
            UserSearchResult(
              id = 0,
              userName = "user",
              avatarUrl = "https://placehold.jp/240x240.png"
            )
          )
        )
      }
    }

    context("#response fail") {
      val exception = Exception()
      coEvery {
        githubApi.searchUsers(query = query)
      } throws exception

      val repository = DefaultSearchRepository(githubApi)

      it("throw Exception") {
        shouldThrow<Exception> { repository.searchUser(query) }
      }
    }
  }
})
