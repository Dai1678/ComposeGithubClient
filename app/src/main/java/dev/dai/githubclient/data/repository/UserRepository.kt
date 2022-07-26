package dev.dai.githubclient.data.repository

import dev.dai.githubclient.model.UserDetail

interface UserRepository {
  suspend fun userDetail(userName: String): UserDetail
}

class DefaultUserRepository : UserRepository {
  override suspend fun userDetail(userName: String): UserDetail {
    TODO("Not yet implemented")
  }
}
