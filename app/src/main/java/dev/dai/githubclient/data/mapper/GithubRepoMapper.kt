package dev.dai.githubclient.data.mapper

import dev.dai.githubclient.data.api.body.GithubRepoBody
import dev.dai.githubclient.model.GithubRepo

fun List<GithubRepoBody>.toGithubRepoList(): List<GithubRepo> {
  return map {
    GithubRepo(
      id = it.id,
      title = it.name,
      description = it.description,
      fork = it.fork,
      url = it.htmlUrl,
      language = it.language,
      stargazersCount = it.stargazersCount
    )
  }
}
