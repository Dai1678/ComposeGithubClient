package dev.dai.githubclient.data.api

import dev.dai.githubclient.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .addHeader(name = "Accept", value = "application/vnd.github+json")
      .addHeader(name = "Authorization", value = "token ${BuildConfig.GITHUB_TOKEN}")
      .build()
    return chain.proceed(request)
  }
}
