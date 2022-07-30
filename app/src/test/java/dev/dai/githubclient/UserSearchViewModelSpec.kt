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
import io.mockk.coVerify
import io.mockk.mockk

class UserSearchViewModelSpec : DescribeSpec({

  extension(MainDispatcherListener())

  val searchRepository = mockk<SearchRepository>()
  lateinit var viewModel: UserSearchViewModel

  val userSearchResult = UserSearchResult(
    id = 0,
    userName = "user",
    avatarUrl = "https://placehold.jp/240x240.png"
  )

  beforeSpec {
    viewModel = UserSearchViewModel(searchRepository)
  }

  describe("#onSearchTextChanged") {
    val inputUserName = "user"
    context("input user") {
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
      viewModel.onSearchTextChanged(inputUserName)
      viewModel.searchUser()

      it("userList should be collect value") {
        viewModel.uiState shouldBe UserSearchUiState(
          searchText = inputUserName,
          userList = listOf(userSearchResult)
        )
      }

      it("verify searchUser") {
        coVerify(exactly = 1) { searchRepository.searchUser(inputUserName) }
      }
    }

    context("response fail") {
      coEvery {
        searchRepository.searchUser(inputUserName)
      } throws Exception()
      viewModel.onSearchTextChanged(inputUserName)
      viewModel.searchUser()

      it("event should be FetchError") {
        viewModel.uiState shouldBe UserSearchUiState(
          searchText = inputUserName,
          event = UserSearchEvent.FetchError
        )
      }

      it("verify searchUser") {
        coVerify(exactly = 1) { searchRepository.searchUser(inputUserName) }
      }
    }
  }

  describe("#consumeEvent") {
    context("consume event") {
      viewModel.consumeEvent()

      it("event should be null") {
        viewModel.uiState.event.shouldBeNull()
      }
    }
  }
})
