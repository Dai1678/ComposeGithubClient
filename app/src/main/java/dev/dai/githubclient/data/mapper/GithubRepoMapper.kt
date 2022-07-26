package dev.dai.githubclient.data.mapper

import dev.dai.githubclient.data.api.body.GithubRepoBody
import dev.dai.githubclient.data.api.body.GithubRepoIndexBody
import dev.dai.githubclient.model.GithubRepo

fun GithubRepoIndexBody.toGithubRepoList(): List<GithubRepo> {
  return items.map { it.toGithubRepo() }
}

private fun GithubRepoBody.toGithubRepo(): GithubRepo {
  return GithubRepo(
    id = id,
    title = title,
    description = description,
    fork = fork,
    url = url,
    language = language,
    stargazerCount = stargazerCount
  )
}
