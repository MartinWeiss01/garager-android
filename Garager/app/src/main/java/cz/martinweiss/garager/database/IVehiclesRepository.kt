package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.model.VehicleWithManufacturer
import kotlinx.coroutines.flow.Flow

interface IVehiclesRepository {
    fun getVehicles(): Flow<List<Vehicle>>
    suspend fun getVehicleById(id: Long): Vehicle
    suspend fun getVehicleWithManufacturerById(id: Long): VehicleWithManufacturer
    suspend fun getManufacturers(): List<Manufacturer>
    suspend fun insertVehicle(vehicle: Vehicle): Long
    suspend fun updateVehicle(vehicle: Vehicle)
}