package dev.dai.githubclient.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dai.githubclient.domain.repository.DefaultSearchRepository
import dev.dai.githubclient.domain.repository.SearchRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindSearchRepository(defaultSearchRepository: DefaultSearchRepository): SearchRepository
}
