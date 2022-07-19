package dev.dai.githubclient.ui.user_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.dai.githubclient.R
import dev.dai.githubclient.domain.model.UserSearchResult
import dev.dai.githubclient.ui.component.LoadingContent
import dev.dai.githubclient.ui.theme.GithubClientTheme

@Composable
fun UserSearchScreen(
  viewModel: UserSearchViewModel = viewModel(),
  scaffoldState: ScaffoldState = rememberScaffoldState()
) {
  val uiState = viewModel.uiState

  Scaffold(
    scaffoldState = scaffoldState,
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_user_search)) }
      )
    }
  ) {
    UserSearchScreenContent(
      searchText = uiState.searchText,
      userList = uiState.userList,
      onSearchTextChanged = { viewModel.onSearchTextChanged(it) },
      onClickSearch = { viewModel.searchUser() },
      onClickUserRow = {
        // TODO ユーザーリポジトリ画面に遷移
      }
    )
  }

  uiState.event?.let {
    when (it) {
      UserSearchEvent.FetchError -> {
        val message = stringResource(id = R.string.message_failed_fetch)
        LaunchedEffect(scaffoldState.snackbarHostState) {
          scaffoldState.snackbarHostState.showSnackbar(message)
          // LaunchedEffect外で呼ぶと先にconsumeされてしまいsnackBarが表示されないので、ここでconsumeする
          viewModel.consumeEvent()
        }
      }
    }
  }

  if (uiState.isLoading) {
    LoadingContent()
  }
}

@Composable
private fun UserSearchScreenContent(
  searchText: String,
  userList: List<UserSearchResult>,
  onSearchTextChanged: (String) -> Unit,
  onClickSearch: () -> Unit,
  onClickUserRow: () -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    UserSearchHeader(
      searchText = searchText,
      onSearchTextChanged = onSearchTextChanged,
      onClickSearch = onClickSearch
    )
    if (userList.isEmpty()) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = stringResource(id = R.string.message_empty_user_search_result),
          style = MaterialTheme.typography.subtitle1
        )
      }
    } else {
      LazyColumn {
        items(userList) {
          UserItem(
            userName = it.userName,
            imageUrl = it.avatarUrl,
            onClickRow = onClickUserRow
          )
        }
      }
    }
  }
}

@Composable
private fun UserSearchHeader(
  searchText: String,
  onSearchTextChanged: (String) -> Unit,
  onClickSearch: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    OutlinedTextField(
      value = searchText,
      onValueChange = onSearchTextChanged,
      label = { Text(text = stringResource(id = R.string.label_user_name)) },
      trailingIcon = {
        AnimatedVisibility(visible = searchText.isNotEmpty()) {
          IconButton(onClick = { onSearchTextChanged("") }) {
            Icon(painter = painterResource(id = R.drawable.ic_cancel), contentDescription = null)
          }
        }
      }
    )
    Spacer(modifier = Modifier.width(16.dp))
    Button(onClick = onClickSearch) {
      Text(text = stringResource(id = R.string.label_search))
    }
  }
}

@Composable
private fun UserItem(
  userName: String,
  imageUrl: String,
  onClickRow: () -> Unit
) {
  Row(
    modifier = Modifier
      .clickable(onClick = onClickRow)
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    AsyncImage(
      model = imageUrl,
      contentDescription = null,
      modifier = Modifier
        .size(56.dp)
        .clip(CircleShape),
      contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.width(16.dp))
    Text(text = userName, style = MaterialTheme.typography.subtitle1)
  }
}

@Preview(showBackground = true)
@Composable
private fun UserSearchScreenContentPreview() {
  GithubClientTheme {
    UserSearchScreenContent(
      searchText = "",
      userList = listOf(
        UserSearchResult(
          id = 0,
          userName = "ユーザー0",
          avatarUrl = "https://placehold.jp/240x240.png"
        ),
        UserSearchResult(
          id = 1,
          userName = "ユーザー1",
          avatarUrl = "https://placehold.jp/240x240.png"
        ),
        UserSearchResult(
          id = 2,
          userName = "ユーザー2",
          avatarUrl = "https://placehold.jp/240x240.png"
        ),
        UserSearchResult(
          id = 3,
          userName = "ユーザー3",
          avatarUrl = "https://placehold.jp/240x240.png"
        ),
        UserSearchResult(
          id = 4,
          userName = "ユーザー4",
          avatarUrl = "https://placehold.jp/240x240.png"
        ),
      ),
      onSearchTextChanged = {},
      onClickSearch = {},
      onClickUserRow = {}
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun UserSearchHeaderPreview() {
  GithubClientTheme {
    UserSearchHeader(
      searchText = "text",
      onSearchTextChanged = {},
      onClickSearch = {}
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun UserItem() {
  GithubClientTheme {
    UserItem(userName = "ユーザー", imageUrl = "https://placehold.jp/240x240.png", onClickRow = {})
  }
}
