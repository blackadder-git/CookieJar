# Overview

{Important! Do not say in this section that this is college assignment. Talk about what you are trying to accomplish as a software engineer to further your learning.}

{Provide a description of the software that you wrote and how it integrates with a Cloud Database. Describe how to use your program.}
When the app is opened, users see a list of different thoughts organized by category. Two numbers represent the total quantity and the number read.
This app connects to a Firebase Realtime Database and can send requests to Create, Read, Update and Delete data.


{Describe your purpose for writing this software.}

{Provide a link to your YouTube demonstration. It should be a 4-5 minute demo of the software running, a walkthrough of the code, and a view of the cloud database.}

[Software Demo Video](https://github.com/blackadder-git/byui/tree/main/cse310/cookie.html)

# Cloud Database

* Google Firebase

- The database consists of a high level user object that stores the names of each user. In addition there is an object for each user that contains zero or more thought objects.
- Each thought has a String field for *quote* and *topic* and a Boolean field for *favorite* and *read*

# Development Environment & Language

- Android Studio 2022.2.1 Patch 2
- JDK 17
- Kotlin 1.8.20

# Useful Websites

- [Firebase](https://firebase.google.com/)
- [Realtime Database]([http://url.link.goes.here](https://firebase.google.com/docs/database))
- [Read and Write on Android]([https://firebase.google.com/](https://firebase.google.com/docs/database/android/read-and-write))

# Future Work

- Prompt the user for a name, save in a file in the application
- Localize data and replace hardcoded strings with data in the strings.xml file
- Check for empty quote
- Add code to update favorite when marked
