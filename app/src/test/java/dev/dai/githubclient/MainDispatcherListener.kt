package dev.dai.githubclient

import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

// @see https://developer.android.com/kotlin/coroutines/test#setting-main-dispatcher
class MainDispatcherListener(
  private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeSpecListener, AfterSpecListener {
  override suspend fun beforeSpec(spec: Spec) {
    super.beforeSpec(spec)
    Dispatchers.setMain(testDispatcher)
  }

  override suspend fun afterSpec(spec: Spec) {
    super.afterSpec(spec)
    Dispatchers.resetMain()
  }
}
