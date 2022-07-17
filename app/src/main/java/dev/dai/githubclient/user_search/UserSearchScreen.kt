package dev.dai.githubclient.user_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dai.githubclient.R
import dev.dai.githubclient.ui.theme.GithubClientTheme

@Composable
fun UserSearchScreen() {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_user_search)) }
      )
    }
  ) {
    Column {
      // TODO connect to ViewModel
      UserSearchHeader(
        searchText = "",
        onSearchTextChanged = {},
        onClickSearch = {}
      )
      LazyColumn {
        items(5) {
          UserItem(userName = "ユーザー", imageUrl = "", onClickRow = {}) // TODO connect to ViewModel
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
    // TODO use Coil-Compose AsyncImage
    Image(
      painter = painterResource(id = R.drawable.ic_droid),
      contentDescription = null,
      modifier = Modifier.size(56.dp)
    )
    Spacer(modifier = Modifier.width(16.dp))
    Text(text = userName, style = MaterialTheme.typography.subtitle1)
  }
}

@Preview(showBackground = true)
@Composable
private fun UserSearchScreenPreview() {
  GithubClientTheme {
    UserSearchScreen()
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
    UserItem(userName = "ユーザー", imageUrl = "", onClickRow = {})
  }
}
