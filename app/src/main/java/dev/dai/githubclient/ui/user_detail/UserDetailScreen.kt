package dev.dai.githubclient.ui.user_detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.dai.githubclient.R
import dev.dai.githubclient.model.GithubRepo
import dev.dai.githubclient.model.User
import dev.dai.githubclient.ui.theme.GithubClientTheme

@ExperimentalMaterialApi
@Composable
private fun UserDetailContent(
  user: User,
  repoList: List<GithubRepo>,
  onClickRepoCard: (String) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize()) {
    UserDetailHeader(
      userName = user.userName,
      fullName = user.fullName,
      imageUrl = user.avatarUrl,
      followerCount = user.followerCount,
      followingCount = user.followingCount
    )
    LazyColumn(
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(repoList, key = { it.id }) {
        GithubRepoCard(
          title = it.title,
          description = it.description,
          language = it.language,
          stargazerCount = it.stargazerCount,
          onClickItem = { onClickRepoCard(it.url) }
        )
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
      AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.size(40.dp))
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

@ExperimentalMaterialApi
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
    onClick = onClickItem
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

@ExperimentalMaterialApi
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
            stargazerCount = 0,
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

@ExperimentalMaterialApi
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
