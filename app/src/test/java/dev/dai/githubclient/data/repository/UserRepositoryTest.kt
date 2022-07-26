package dev.dai.githubclient.data.repository

import dev.dai.githubclient.data.api.GithubApi
import dev.dai.githubclient.data.api.body.GithubRepoIndexBody
import dev.dai.githubclient.data.api.body.UserBody
import dev.dai.githubclient.model.User
import dev.dai.githubclient.model.UserDetail
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class UserRepositoryTest : DescribeSpec({

  val githubApi = mockk<GithubApi>()

  lateinit var repository: UserRepository

  beforeTest {
    repository = DefaultUserRepository(githubApi)
  }

  describe("#userDetail") {
    val userName = "user"
    context("response success") {
      coEvery {
        githubApi.user(userName)
      } returns UserBody(
        id = 0,
        userName = userName,
        fullName = "user user",
        avatarUrl = "https://placehold.jp/240x240.png",
        following = 0,
        followers = 0
      )
      coEvery {
        githubApi.userGithubRepo(userName, 50)
      } returns GithubRepoIndexBody(
        items = listOf()
      )

      val response = repository.userDetail(userName)

      it("return should be UserDetail") {
        response shouldBe UserDetail(
          User(
            id = 0,
            userName = userName,
            fullName = "user user",
            avatarUrl = "https://placehold.jp/240x240.png",
            followingCount = 0,
            followerCount = 0
          ),
          githubRepoList = listOf()
        )
      }

      it("verify") {
        coVerify(exactly = 1) { githubApi.user(userName) }
        coVerify(exactly = 1) { githubApi.userGithubRepo(userName, 50) }
      }
    }

    context("response fail") {
      coEvery {
        githubApi.user(userName)
      } throws Exception()

      it("throw Exception") {
        shouldThrow<Exception> { repository.userDetail(userName) }
      }

      it("verify") {
        coVerify(exactly = 0) { githubApi.user(userName) }
        coVerify(exactly = 0) { githubApi.userGithubRepo(userName, 50) }
      }
    }
  }

})
