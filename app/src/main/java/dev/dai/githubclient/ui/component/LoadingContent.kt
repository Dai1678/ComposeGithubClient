package dev.dai.githubclient.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingContent() {
  Surface(
    color = Color.Transparent,
    modifier = Modifier.fillMaxSize()
  ) {
    Box(contentAlignment = Alignment.Center) {
      CircularProgressIndicator(color = MaterialTheme.colors.secondary)
    }
  }
}
