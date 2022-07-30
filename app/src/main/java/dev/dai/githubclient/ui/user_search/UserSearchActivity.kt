package dev.dai.githubclient.ui.user_search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.dai.githubclient.ui.theme.GithubClientTheme
import dev.dai.githubclient.ui.user_detail.UserDetailActivity

@ExperimentalMaterialApi
@AndroidEntryPoint
class UserSearchActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GithubClientTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          UserSearchScreen(
            navigateToUserDetail = { userName ->
              startActivity(UserDetailActivity.createIntent(this, userName))
            }
          )
        }
      }
    }
  }
}
