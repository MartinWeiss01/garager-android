package cz.martinweiss.garager

import android.app.Application
import android.content.Context
import cz.martinweiss.garager.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GaragerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidContext(this@GaragerApplication)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                daoModule,
                databaseModule,
                dataStoreModule
            ))
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}