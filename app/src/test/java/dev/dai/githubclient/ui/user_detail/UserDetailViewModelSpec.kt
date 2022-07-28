package dev.dai.githubclient.ui.user_detail

import dev.dai.githubclient.MainDispatcherListener
import dev.dai.githubclient.data.repository.UserRepository
import dev.dai.githubclient.model.GithubRepo
import dev.dai.githubclient.model.User
import dev.dai.githubclient.model.UserDetail
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserDetailViewModelSpec : DescribeSpec({

  extension(MainDispatcherListener())

  val userRepository = mockk<UserRepository>()
  lateinit var viewModel: UserDetailViewModel

  val user = User(
    id = 0,
    userName = "user",
    fullName = "user user",
    avatarUrl = "https://placehold.jp/240x240.png",
    followerCount = 0,
    followingCount = 0
  )

  beforeSpec {
    viewModel = UserDetailViewModel(userRepository)
  }

  describe("#fetchUserDetail") {
    val userName = "user"
    context("success") {
      coEvery {
        userRepository.userDetail(userName)
      } returns UserDetail(
        user = user,
        githubRepoList = listOf(
          GithubRepo(
            id = 0,
            title = "title1",
            description = null,
            fork = true,
            url = "https://github.com",
            language = "Kotlin",
            stargazerCount = 0
          ),
          GithubRepo(
            id = 1,
            title = "title2",
            description = null,
            fork = false,
            url = "https://github.com",
            language = "Kotlin",
            stargazerCount = 0
          )
        )
      )

      viewModel.fetchUserDetail(userName)

      it("uiState should be collect value") {
        viewModel.uiState shouldBe UserDetailUiState(
          user = user,
          githubRepoList = listOf(
            GithubRepo(
              id = 1,
              title = "title2",
              description = null,
              fork = false,
              url = "https://github.com",
              language = "Kotlin",
              stargazerCount = 0
            )
          )
        )
      }

      it("verify") {
        coVerify(exactly = 1) { userRepository.userDetail(userName) }
      }
    }
  }

})
