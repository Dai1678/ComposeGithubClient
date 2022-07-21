package dev.dai.githubclient

import dev.dai.githubclient.data.repository.SearchRepository
import dev.dai.githubclient.ui.user_search.UserSearchViewModel
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class UserSearchViewModelSpec : DescribeSpec({

  val searchRepository = mockk<SearchRepository>()

  beforeTest {
    Dispatchers.setMain(StandardTestDispatcher())
    coroutineTestScope = true
  }

  afterTest {
    Dispatchers.resetMain()
  }

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

})
