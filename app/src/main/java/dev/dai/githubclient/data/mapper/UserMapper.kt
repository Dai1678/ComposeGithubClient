package dev.dai.githubclient.data.mapper

import dev.dai.githubclient.data.api.body.UserBody
import dev.dai.githubclient.model.User

fun UserBody.toUser(): User {
  return User(
    id = id,
    userName = userName,
    fullName = fullName,
    avatarUrl = avatarUrl,
    followerCount = followers,
    followingCount = following
  )
}
