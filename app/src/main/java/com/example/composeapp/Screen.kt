package com.example.composeapp

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ListScreen : Screen("list_screen")
    object AuthorScreen : Screen("author_screen")
}