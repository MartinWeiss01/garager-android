package cz.mendelu.pef.golf.di

import cz.mendelu.pef.golf.database.PlayersDao
import cz.mendelu.pef.golf.database.PlayersDatabase
import org.koin.dsl.module

val daoModule = module {
    fun providePlayersDao(database: PlayersDatabase): PlayersDao {
        return database.PlayersDao()
    }

    single { providePlayersDao(get()) }
}