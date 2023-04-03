package cz.martinweiss.garager

import android.app.Application
import android.content.Context
import cz.martinweiss.garager.di.daoModule
import cz.martinweiss.garager.di.databaseModule
import cz.martinweiss.garager.di.repositoryModule
import cz.martinweiss.garager.di.viewModelModule
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
                databaseModule
            ))
        }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}