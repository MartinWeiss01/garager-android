package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.model.VehicleWithManufacturer
import kotlinx.coroutines.flow.Flow

class VehiclesRepositoryImpl(private val dao: VehiclesDao) : IVehiclesRepository {
    override fun getVehicles(): Flow<List<Vehicle>> {
        return dao.getVehicles()
    }

    override fun getVehicleById(id: Long): Flow<VehicleWithManufacturer> {
        return dao.getVehicleById(id)
    }

    override suspend fun getVehicleWithManufacturerById(id: Long): VehicleWithManufacturer {
        return dao.getVehicleWithManufacturerById(id)
    }

    override suspend fun getManufacturers(): List<Manufacturer> {
        return dao.getManufacturers()
    }

    override suspend fun insertVehicle(vehicle: Vehicle): Long {
        return dao.insertVehicle(vehicle)
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        return dao.updateVehicle(vehicle)
    }
}