package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface IVehiclesRepository {
    fun getVehicles(): Flow<List<Vehicle>>
    suspend fun getVehicleById(id: Long): Vehicle
    fun getManufacturers(): Flow<List<Manufacturer>>
    suspend fun insertVehicle(vehicle: Vehicle): Long
    suspend fun updateVehicle(vehicle: Vehicle)
}