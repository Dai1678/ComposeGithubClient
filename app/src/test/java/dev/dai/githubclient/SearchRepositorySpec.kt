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
import io.mockk.coVerify
import io.mockk.mockk

class SearchRepositorySpec : DescribeSpec({

  val githubApi = mockk<GithubApi>()
  lateinit var repository: DefaultSearchRepository

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

  beforeSpec {
    repository = DefaultSearchRepository(githubApi)
  }

  describe("#searchUser") {
    val query = "user"
    context("response success") {
      coEvery {
        githubApi.searchUsers(query)
      } returns userSearchResultIndexBody
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

      it("verify searchUsers") {
        coVerify(exactly = 1) { githubApi.searchUsers(query) }
      }
    }

    context("#response fail") {
      coEvery {
        githubApi.searchUsers(query)
      } throws Exception()

      it("throw Exception") {
        shouldThrow<Exception> { repository.searchUser(query) }
      }

      it("verify searchUsers") {
        coVerify(exactly = 0) { githubApi.searchUsers(query) }
      }
    }
  }
})
