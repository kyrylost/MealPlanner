package dev.stukalo.mealplanner.data.network.core.di

import dev.stukalo.mealplanner.data.network.core.client.client
import org.koin.dsl.module

val dataNetworkModule = module {
    single { client() }
}