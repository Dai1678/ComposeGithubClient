package dev.dai.githubclient.ui.user_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dev.dai.githubclient.ui.theme.GithubClientTheme

class UserDetailActivity : ComponentActivity() {

  @ExperimentalMaterialApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val userName = intent.getStringExtra(KEY_USER_NAME) ?: throw IllegalArgumentException()

    setContent {
      GithubClientTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          UserDetailScreen(userName = userName)
        }
      }
    }
  }

  companion object {
    private const val KEY_USER_NAME = "USER_NAME"

    fun createIntent(context: Context, userName: String): Intent {
      return Intent(context, UserDetailActivity::class.java).apply {
        putExtra(KEY_USER_NAME, userName)
      }
    }
  }
}
