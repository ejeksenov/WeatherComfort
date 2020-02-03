package kz.weather.weathercomfort

import android.app.Application
import kz.weather.weathercomfort.di.presentationModule
import kz.weather.data.di.databaseModule
import kz.weather.data.di.networkingModule
import kz.weather.data.di.repositoryModule
import kz.weather.domain.di.interactionModule
import kz.weather.weathercomfort.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            modules(appModules + domainModules + dataModules)
        }
    }
}

val appModules = listOf(presentationModule, appModule)
val domainModules = listOf(interactionModule)
val dataModules = listOf(networkingModule, repositoryModule, databaseModule)