package cz.mendelu.pef.golf.di

import cz.mendelu.pef.golf.database.IPlayersRepository
import cz.mendelu.pef.golf.database.PlayersDao
import cz.mendelu.pef.golf.database.PlayersRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    fun providePlayersRepository(dao: PlayersDao): IPlayersRepository {
        return PlayersRepositoryImpl(dao)
    }

    single { providePlayersRepository(get()) }
}