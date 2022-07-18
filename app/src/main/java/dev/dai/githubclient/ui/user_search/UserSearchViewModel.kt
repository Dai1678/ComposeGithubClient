package dev.dai.githubclient.ui.user_search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.githubclient.domain.model.UserSearchResult
import dev.dai.githubclient.domain.repository.SearchRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
  private val searchRepository: SearchRepository
) : ViewModel() {

  var uiState by mutableStateOf(UserSearchUiState())
    private set

  fun onSearchTextChanged(userName: String) {
    uiState = uiState.copy(searchText = userName)
  }

  fun searchUser() {
    uiState = uiState.copy(isLoading = true)
    viewModelScope.launch {
      try {
        val result = searchRepository.searchUser(uiState.searchText)
        uiState = if (result.totalCount == 0) {
          uiState.copy(event = UserSearchEvent.UserNotFound)
        } else {
          uiState.copy(userList = result.userList)
        }
      } catch (e: Exception) {
        uiState = uiState.copy(event = UserSearchEvent.FetchError)
      } finally {
        uiState = uiState.copy(isLoading = false)
      }
    }
  }

  fun consumeEvent() {
    uiState = uiState.copy(event = null)
  }
}

data class UserSearchUiState(
  val isLoading: Boolean = false,
  val searchText: String = "",
  val userList: List<UserSearchResult> = emptyList(),
  val event: UserSearchEvent? = null
)

sealed interface UserSearchEvent {
  object FetchError : UserSearchEvent
  object UserNotFound : UserSearchEvent
}
