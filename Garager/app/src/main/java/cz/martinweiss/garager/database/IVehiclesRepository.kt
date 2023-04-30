package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.Fueling
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.model.VehicleWithManufacturer
import kotlinx.coroutines.flow.Flow

interface IVehiclesRepository {
    fun getVehicles(): Flow<List<Vehicle>>
    fun getVehicleById(id: Long): Flow<VehicleWithManufacturer>
    suspend fun getVehicleWithManufacturerById(id: Long): VehicleWithManufacturer
    suspend fun getManufacturers(): List<Manufacturer>
    suspend fun insertVehicle(vehicle: Vehicle): Long
    suspend fun updateVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
    fun getFuelingRecords(): Flow<List<Fueling>>
    fun getFuelingRecordById(id: Long): Flow<Fueling>
    suspend fun insertFueling(fueling: Fueling): Long
    suspend fun updateFueling(fueling: Fueling)
    suspend fun deleteFueling(fueling: Fueling)
}