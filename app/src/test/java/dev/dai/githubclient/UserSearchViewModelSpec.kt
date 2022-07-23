package dev.dai.githubclient

import dev.dai.githubclient.data.repository.SearchRepository
import dev.dai.githubclient.model.UserSearchResult
import dev.dai.githubclient.model.UserSearchResultIndex
import dev.dai.githubclient.ui.user_search.UserSearchEvent
import dev.dai.githubclient.ui.user_search.UserSearchUiState
import dev.dai.githubclient.ui.user_search.UserSearchViewModel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserSearchViewModelSpec : DescribeSpec({

  extension(MainDispatcherListener())

  val searchRepository = mockk<SearchRepository>()

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
      coEvery {
        searchRepository.searchUser(inputUserName)
      } returns UserSearchResultIndex(
        totalCount = 1,
        userList = listOf(userSearchResult)
      )
      val viewModel = UserSearchViewModel(searchRepository)
      viewModel.onSearchTextChanged(inputUserName)
      viewModel.searchUser()

      it("userList should be collect value") {
        viewModel.uiState shouldBe UserSearchUiState(
          searchText = inputUserName,
          userList = listOf(userSearchResult)
        )
      }
    }

    context("response fail") {
      val exception = Exception()
      coEvery {
        searchRepository.searchUser(inputUserName)
      } throws exception
      val viewModel = UserSearchViewModel(searchRepository)
      viewModel.onSearchTextChanged(inputUserName)
      viewModel.searchUser()

      it("event should be FetchError") {
        viewModel.uiState shouldBe UserSearchUiState(
          searchText = inputUserName,
          event = UserSearchEvent.FetchError
        )
      }
    }
  }

  describe("#consumeEvent") {
    context("consume event") {
      val viewModel = UserSearchViewModel(searchRepository)
      viewModel.consumeEvent()

      it("event should be null") {
        viewModel.uiState.event.shouldBeNull()
      }
    }
  }
})
