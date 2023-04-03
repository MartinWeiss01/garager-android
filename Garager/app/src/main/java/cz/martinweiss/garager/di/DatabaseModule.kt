package cz.martinweiss.garager.di

import cz.martinweiss.garager.GaragerApplication
import cz.martinweiss.garager.database.VehiclesDatabase
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(): VehiclesDatabase {
        return VehiclesDatabase.getDatabase(GaragerApplication.appContext)
    }

    single { provideDatabase() }
}