package dev.stukalo.mealplanner

import android.app.Application
import dev.stukalo.mealplanner.data.database.di.databaseBuilderModule
import dev.stukalo.mealplanner.data.database.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MealPlannerApp: Application() {
    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@MealPlannerApp)
//            androidLogger()
//            modules(
//                databaseBuilderModule,
//                databaseModule,
//            )
//        }
    }
}