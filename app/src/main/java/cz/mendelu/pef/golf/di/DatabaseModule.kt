package cz.mendelu.pef.golf.di

import cz.mendelu.pef.golf.GolfApplication
import cz.mendelu.pef.golf.database.PlayersDatabase
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(): PlayersDatabase {
        return PlayersDatabase.getDatabase(GolfApplication.appContext)
    }

    single { provideDatabase() }

}