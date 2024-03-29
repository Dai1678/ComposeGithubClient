package dev.dai.githubclient.ui.user_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dai.githubclient.data.repository.UserRepository
import dev.dai.githubclient.model.GithubRepo
import dev.dai.githubclient.model.User
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
  private val userRepository: UserRepository
) : ViewModel() {

  var uiState by mutableStateOf(UserDetailUiState())
    private set

  fun fetchUserDetail(userName: String) {
    uiState = uiState.copy(isLoading = true)
    viewModelScope.launch {
      try {
        val result = userRepository.userDetail(userName)
        uiState = uiState.copy(
          user = result.user,
          githubRepoList = result.githubRepoList.filter { !it.fork }
        )
      } catch (e: Exception) {
        Timber.e(e)
        uiState = uiState.copy(event = UserDetailEvent.FetchError)
      } finally {
        uiState = uiState.copy(isLoading = false)
      }
    }
  }

  fun consumeEvent() {
    uiState = uiState.copy(event = null)
  }
}

data class UserDetailUiState(
  val isLoading: Boolean = false,
  val user: User? = null,
  val githubRepoList: List<GithubRepo> = emptyList(),
  val event: UserDetailEvent? = null
)

sealed interface UserDetailEvent {
  object FetchError : UserDetailEvent
}
