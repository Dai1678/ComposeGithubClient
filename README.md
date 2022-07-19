# ComposeGithubClient
Github Client for Android Jetpack Compose

## Development
### Feature
- Search user by Github username.
- Show user information and user's repository list

|UserSearch|UserRepository|
|---|---|
|<img src="https://user-images.githubusercontent.com/19250035/179735564-ae24f0f5-eb9c-4168-ba12-8042c59b7f85.png" width=300>||

### Getting Started
Required latest Android Studio.

This project build to need Github personal access token.
You can create it from [this page](https://github.com/settings/tokens) and select scopes **repo** and **user**.

## Architecture
2Layer Architecture.
- UI layer
  - Activity
  - Jetpack Compose
    - Screen Composable
    - Content Composable
    - Component Composables
  - ViewModel
- Data Layer
  - Repository
  - ApiClient

FYI: https://developer.android.com/topic/architecture#recommended-app-arch

## Tech Stacks
### UI
- Multi Activity + Jetpack Compose
- Android Architecture Component

### DI
- Dagger Hilt

### API
- Retrofit2
- OkHttp3
- Gson

