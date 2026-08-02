package dev.stukalo.mealplanner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
