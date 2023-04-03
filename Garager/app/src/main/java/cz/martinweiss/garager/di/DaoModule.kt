package cz.martinweiss.garager.di

import cz.martinweiss.garager.database.VehiclesDao
import cz.martinweiss.garager.database.VehiclesDatabase
import org.koin.dsl.module

val daoModule = module {
    fun provideVehiclesDao(database: VehiclesDatabase): VehiclesDao {
        return database.VehiclesDao()
    }

    single { provideVehiclesDao(get()) }
}