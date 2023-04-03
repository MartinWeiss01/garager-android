package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.flow.Flow

class VehiclesRepositoryImpl(private val dao: VehiclesDao) : IVehiclesRepository {
    override fun getVehicles(): Flow<List<Vehicle>> {
        return dao.getVehicles()
    }

    override suspend fun insertVehicle(vehicle: Vehicle): Long {
        return dao.insertVehicle(vehicle)
    }
}