# MyTodoTaskManager

Application to remind a user different kinds of tasks like watch series, movies and read books.

Android Application developed with Kotlin, Clean architecture, MVVM pattern, Koin, Coroutines, Retrofit, Room, JWT and MockK for testing

The app uses coroutines and Room database as local cache to avoid API calls

The API (Back-end) URL is deployed in Heroku: https://pers-task-manager.herokuapp.com

You can find the API project in: https://github.com/Steampunk1453/todo-task-manager

## Local environment

- You can test the application running it in local, this is the default API server:

            buildConfigField "String", "SERVER_URL", '"https://pers-task-manager.herokuapp.com/api/"'

- Also, you can run API project in local, following:

[Readme todo-task-manager](https://github.com/Steampunk1453/todo-task-manager/blob/master/README.md#local-environment)

- In the app file build.gradle you have to add:

    - If you use emulator

            buildConfigField "String", "SERVER_URL", '"http://10.0.2.2:8080/api/"'

    - If you use your phone

            buildConfigField "String", "SERVER_URL", '"http://[your local IP address]:8080/api/"'
 

