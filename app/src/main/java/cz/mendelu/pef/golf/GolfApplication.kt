package cz.mendelu.pef.golf

import android.app.Application
import android.content.Context
import cz.mendelu.pef.golf.di.daoModule
import cz.mendelu.pef.golf.di.databaseModule
import cz.mendelu.pef.golf.di.repositoryModule
import cz.mendelu.pef.golf.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GolfApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidContext(this@GolfApplication)
            modules(listOf(
                // todo moduly
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