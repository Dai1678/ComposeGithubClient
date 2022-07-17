package dev.dai.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dai.githubclient.ui.theme.GithubClientTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GithubClientTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {

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
      label = {
        Text(text = "ユーザー名")
      },
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
      Text(text = "検索")
    }
  }
}

@Composable
private fun UserItem(
  userName: String,
  imageUrl: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // TODO coil AsyncImage
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
    UserItem(userName = "ユーザー", imageUrl = "")
  }
}
