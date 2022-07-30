package dev.dai.githubclient.ui.user_search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.githubclient.data.repository.SearchRepository
import dev.dai.githubclient.model.UserSearchResult
import kotlinx.coroutines.launch
import timber.log.Timber
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
        uiState = uiState.copy(userList = result.userList)
      } catch (e: Exception) {
        Timber.e(e)
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
}
