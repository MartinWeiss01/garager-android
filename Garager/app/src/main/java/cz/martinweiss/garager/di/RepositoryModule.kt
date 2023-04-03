package cz.martinweiss.garager.di

import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.database.VehiclesDao
import cz.martinweiss.garager.database.VehiclesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    fun provideVehiclesRepository(dao: VehiclesDao): IVehiclesRepository {
        return VehiclesRepositoryImpl(dao)
    }

    single { provideVehiclesRepository(get()) }
}