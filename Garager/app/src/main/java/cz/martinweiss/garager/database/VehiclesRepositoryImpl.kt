package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.*
import kotlinx.coroutines.flow.Flow

class VehiclesRepositoryImpl(private val dao: VehiclesDao) : IVehiclesRepository {
    override fun getVehicles(): Flow<List<Vehicle>> {
        return dao.getVehicles()
    }

    override suspend fun getAvailableVehicles(): List<Vehicle> {
        return dao.getAvailableVehicles()
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

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        return dao.deleteVehicle(vehicle)
    }

    override fun getFuelingRecords(): Flow<List<Fueling>> {
        return dao.getFuelingRecords()
    }

    override fun getFuelingRecordById(id: Long): Flow<Fueling> {
        return dao.getFuelingRecordById(id)
    }

    override suspend fun insertFueling(fueling: RawFueling): Long {
        return dao.insertFueling(fueling)
    }

    override suspend fun updateFueling(fueling: RawFueling) {
        return dao.updateFueling(fueling)
    }

    override suspend fun deleteFueling(fueling: RawFueling) {
        return dao.deleteFueling(fueling)
    }
}