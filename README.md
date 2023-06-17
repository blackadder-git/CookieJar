# Overview
The purpose of this software is to provide a list of inspirational thoughts organized by topic. Two numbers indicate the total number of each thought and the number of thoughts already looked at. If clicked, the category will display a random thought with options to mark as favorite, delete or close. Another screen allows the user to submit new thoughts.

The main screen *reads* data from a Firebase Realtime Database. When a user views a thought, the database is *updated*. If the user clicks *delete* the record is removed from the cloud. If the user submits data from the add new screen, a new record is *created* 

I wrote this program after my daughters created a real jar with notes in a church activity. I liked the ideas and wanted to create a virtual equivalent.

[Software Demo Video]((https://blackadder-git.github.io/byui/cse310/cookie.html)

# Cloud Database
* Google Firebase Realtime Database
- The database consists of a high level user object that stores the names of each user.
- In addition there is an object for each user that contains zero or more thought objects.
- Each thought has a String field for *quote* and *topic* and a Boolean field for *favorite* and *read* for a total of four fields

# Development Environment & Language
- Android Studio 2022.2.1 Patch 2
- JDK 17
- Kotlin 1.8.20
- Firebase Realtime Database

# Useful Websites
- [Firebase](https://firebase.google.com/)
- [Realtime Database](https://firebase.google.com/docs/database)
- [Read and Write on Android](https://firebase.google.com/docs/database/android/read-and-write)

# Future Work
- Prompt the user for a name, save in a file in the application
- Localize data and replace hardcoded strings with data in the strings.xml file
- Check for empty quote
- Add code to update favorite when marked
