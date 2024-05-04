# GitHub Mobile Cloning App

---

## Description
This Android application is a clone of the GitHub mobile app, built using Kotlin and Android Studio. It provides users with features to explore repositories, view user profiles, search for repositories, and search for users.



## Features

1. **Explore Repositories:**
   - Users can browse through a list of repositories available on GitHub.
   - Detailed information about each repository, including name, description, watches

2. **View User Profiles:**
   - Users can view profiles of GitHub users.
   - User profiles display essential information such as username, bio, followers, following, repositories

3. **Search for Repositories:**
   - Users can search for repositories by entering keywords or topics.
   - Search results are displayed in a list format, allowing users to explore repositories matching their search query.

4. **Search for Users:**
   - Users can search for other GitHub users by their usernames.
   - Search results display user profiles, enabling users to discover and connect with other GitHub users.

## Tech Used
- **Framework:** Android Studio
- **Programming Language:** Kotlin
- **API Integration:** GitHub API (for fetching repository and user data)

## Installation
1. Clone the repository from GitHub.
2. Open the project in Android Studio.
3. Add your GitHub token and API base URL to the `build.gradle` file:
   - Open the `build.gradle` file for your app module.
   - Add the following lines inside the `android` block:

     ```groovy
     buildConfigField("String","API_KEY", "\"YOUR_TOKEN_HERE\"")
     buildConfigField("String","BASE_URL", "\"https://api.github.com/\"")
     ```

   Replace `"YOUR_TOKEN_HERE"` with your actual GitHub token.
4. Build and run the project on an Android device or emulator.


