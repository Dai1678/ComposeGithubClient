package dev.dai.githubclient.infra.api.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.githubclient.BuildConfig
import dev.dai.githubclient.infra.api.AuthorizationInterceptor
import dev.dai.githubclient.infra.api.GithubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  private const val BASE_URL = "https://api.github.com"

  @Provides
  fun provideGithubApi(): GithubApi {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(provideGson()))
      .client(provideOkhttpClient())
      .build()
      .create(GithubApi::class.java)
  }

  private fun provideOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(provideLogging())
      .addInterceptor(AuthorizationInterceptor())
      .build()
  }

  private fun provideGson(): Gson {
    return GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create()
  }

  private fun provideLogging() = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
      this.level = HttpLoggingInterceptor.Level.BODY
    }
  }
}
