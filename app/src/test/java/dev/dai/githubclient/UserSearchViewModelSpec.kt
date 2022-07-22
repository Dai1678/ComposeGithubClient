package dev.dai.githubclient

import dev.dai.githubclient.data.repository.SearchRepository
import dev.dai.githubclient.model.UserSearchResult
import dev.dai.githubclient.model.UserSearchResultIndex
import dev.dai.githubclient.ui.user_search.UserSearchUiState
import dev.dai.githubclient.ui.user_search.UserSearchViewModel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class UserSearchViewModelSpec : DescribeSpec({

  val searchRepository = mockk<SearchRepository>()

  beforeTest {
    Dispatchers.setMain(UnconfinedTestDispatcher())
  }

  afterTest {
    Dispatchers.resetMain()
  }

  val userSearchResult = UserSearchResult(
    id = 0,
    userName = "user",
    avatarUrl = "https://placehold.jp/240x240.png"
  )

  describe("#onSearchTextChanged") {
    val inputUserName = "user"
    context("input user") {
      val viewModel = UserSearchViewModel(searchRepository)
      viewModel.onSearchTextChanged(inputUserName)

      it("searchText should be user") {
        viewModel.uiState.searchText shouldBe inputUserName
      }
    }
  }

  describe("#searchUser") {
    val inputUserName = "user"
    context("response success") {
      runTest {
        coEvery {
          searchRepository.searchUser(inputUserName)
        } returns UserSearchResultIndex(
          totalCount = 1,
          userList = listOf(userSearchResult)
        )
        val viewModel = UserSearchViewModel(searchRepository)
        viewModel.onSearchTextChanged(inputUserName)
        viewModel.searchUser()

        it("return should be userList") {
          viewModel.uiState shouldBe UserSearchUiState(
            searchText = inputUserName,
            userList = listOf(userSearchResult)
          )
        }
      }
    }
  }

})
