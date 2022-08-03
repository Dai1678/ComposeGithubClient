package dev.dai.githubclient.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.githubclient.data.repository.DefaultSearchRepository
import dev.dai.githubclient.data.repository.DefaultUserRepository
import dev.dai.githubclient.data.repository.SearchRepository
import dev.dai.githubclient.data.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindSearchRepository(defaultSearchRepository: DefaultSearchRepository): SearchRepository

  @Binds
  abstract fun bindUserRepository(defaultUserRepository: DefaultUserRepository): UserRepository
}
