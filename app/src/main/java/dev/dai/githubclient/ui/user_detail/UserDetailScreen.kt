package dev.dai.githubclient.ui.user_detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
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
import dev.dai.githubclient.ui.theme.GithubClientTheme

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
