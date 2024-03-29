package dev.dai.githubclient.ui.user_detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.dai.githubclient.R
import dev.dai.githubclient.model.GithubRepo
import dev.dai.githubclient.model.User
import dev.dai.githubclient.ui.component.EmptyListContent
import dev.dai.githubclient.ui.component.LoadingContent
import dev.dai.githubclient.ui.theme.GithubClientTheme

@Composable
fun UserDetailScreen(
  userName: String,
  viewModel: UserDetailViewModel = viewModel(),
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onClickNavigationIcon: () -> Unit
) {
  val context = LocalContext.current
  val uiState = viewModel.uiState

  LaunchedEffect(Unit) {
    viewModel.fetchUserDetail(userName)
  }

  Scaffold(
    scaffoldState = scaffoldState,
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_user_repository)) },
        navigationIcon = {
          IconButton(onClick = onClickNavigationIcon) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
          }
        }
      )
    }
  ) {
    uiState.user?.let {
      UserDetailContent(
        user = it,
        repoList = uiState.githubRepoList,
        onClickRepoCard = { url ->
          val intent = CustomTabsIntent.Builder().build()
          intent.launchUrl(context, Uri.parse(url))
        }
      )
    }
  }

  uiState.event?.let {
    when (it) {
      UserDetailEvent.FetchError -> {
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
private fun UserDetailContent(
  user: User,
  repoList: List<GithubRepo>,
  onClickRepoCard: (String) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    UserDetailHeader(
      userName = user.userName,
      fullName = user.fullName.orEmpty(),
      imageUrl = user.avatarUrl,
      followerCount = user.followerCount,
      followingCount = user.followingCount
    )
    if (repoList.isEmpty()) {
      EmptyListContent(text = stringResource(id = R.string.message_empty_user_repository))
    } else {
      LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(repoList, key = { it.id }) {
          GithubRepoCard(
            title = it.title,
            description = it.description,
            language = it.language,
            stargazerCount = it.stargazersCount,
            onClickItem = { onClickRepoCard(it.url) }
          )
        }
      }
    }
  }
}

@Composable
private fun UserDetailHeader(
  userName: String,
  fullName: String,
  imageUrl: String,
  followerCount: Int,
  followingCount: Int
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
      )
      Spacer(modifier = Modifier.width(16.dp))
      Column {
        Text(text = fullName, style = MaterialTheme.typography.subtitle1)
        Text(
          text = userName,
          style = MaterialTheme.typography.body2,
          color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
        )
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
      modifier = Modifier.align(Alignment.End),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(painter = painterResource(id = R.drawable.ic_user_circle), contentDescription = null)
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = buildAnnotatedString {
          withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(followerCount.toString())
          }
          append(" ")
          append(stringResource(id = R.string.label_followers))
          append("・")
          withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(followingCount.toString())
          }
          append(" ")
          append(stringResource(id = R.string.label_following))
        },
        style = MaterialTheme.typography.body2
      )
    }
  }
}

@Composable
private fun GithubRepoCard(
  title: String,
  description: String?,
  language: String?,
  stargazerCount: Int,
  onClickItem: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    onClick = onClickItem,
    elevation = 3.dp
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = title, style = MaterialTheme.typography.subtitle2)
      Text(
        text = description.orEmpty(),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
      )
      Row(
        modifier = Modifier.align(Alignment.End),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = language.orEmpty(), style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.width(16.dp))
        Image(painter = painterResource(id = R.drawable.ic_star_outline), contentDescription = null)
        Text(text = stargazerCount.toString(), style = MaterialTheme.typography.body2)
      }
    }
  }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun UserDetailContentPreview() {
  GithubClientTheme {
    Surface {
      UserDetailContent(
        user = User(
          id = 0,
          userName = "Dai1678",
          fullName = "miyamoto.dai",
          avatarUrl = "https://avatars.githubusercontent.com/u/19250035?v=4",
          followerCount = 0,
          followingCount = 0
        ),
        repoList = listOf(
          GithubRepo(
            id = 0,
            title = "リポジトリ名",
            description = "リポジトリの説明",
            language = "Kotlin",
            stargazersCount = 0,
            fork = false,
            url = "https://github.com/Dai1678/ComposeGithubClient"
          )
        ),
        onClickRepoCard = {}
      )
    }
  }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun UserDetailHeaderPreview() {
  GithubClientTheme {
    Surface {
      UserDetailHeader(
        userName = "Dai1678",
        fullName = "miyamoto.dai",
        imageUrl = "https://avatars.githubusercontent.com/u/19250035?v=4",
        followerCount = 10,
        followingCount = 1
      )
    }
  }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GithubRepoItemPreview() {
  GithubClientTheme {
    Surface {
      GithubRepoCard(
        title = "リポジトリ名",
        description = "リポジトリの説明",
        language = "Kotlin",
        stargazerCount = 0,
        onClickItem = {}
      )
    }
  }
}
