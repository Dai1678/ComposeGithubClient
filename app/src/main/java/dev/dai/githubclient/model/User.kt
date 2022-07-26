package dev.dai.githubclient.model

data class User(
  val id: Int,
  val userName: String,
  val fullName: String,
  val avatarUrl: String,
  val followerCount: Int,
  val followingCount: Int
)
