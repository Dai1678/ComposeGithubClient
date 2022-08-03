package dev.dai.githubclient.data.repository

import dev.dai.githubclient.data.api.GithubApi
import dev.dai.githubclient.data.api.body.GithubRepoBody
import dev.dai.githubclient.data.api.body.UserBody
import dev.dai.githubclient.model.GithubRepo
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
    val sort = "pushed"
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
        githubApi.userGithubRepo(userName, 50, sort)
      } returns listOf(
        GithubRepoBody(
          id = 0,
          name = "ComposeGithubClient",
          description = "Github Client for Android Jetpack Compose",
          fork = false,
          htmlUrl = "https://github.com/Dai1678/ComposeGithubClient",
          language = "Kotlin",
          stargazersCount = 0
        )
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
          githubRepoList = listOf(
            GithubRepo(
              id = 0,
              title = "ComposeGithubClient",
              description = "Github Client for Android Jetpack Compose",
              fork = false,
              url = "https://github.com/Dai1678/ComposeGithubClient",
              language = "Kotlin",
              stargazersCount = 0
            )
          )
        )
      }

      it("verify") {
        coVerify(exactly = 1) { githubApi.user(userName) }
        coVerify(exactly = 1) { githubApi.userGithubRepo(userName, 50, sort) }
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
        coVerify(exactly = 0) { githubApi.userGithubRepo(userName, 50, sort) }
      }
    }
  }

})
