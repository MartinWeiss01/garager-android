package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.*
import kotlinx.coroutines.flow.Flow

interface IVehiclesRepository {
    fun getVehicles(): Flow<List<Vehicle>>
    suspend fun getAvailableVehicles(): List<Vehicle>
    fun getVehicleById(id: Long): Flow<VehicleWithManufacturer>
    suspend fun getVehicleWithManufacturerById(id: Long): VehicleWithManufacturer
    suspend fun getManufacturers(): List<Manufacturer>
    suspend fun insertVehicle(vehicle: Vehicle): Long
    suspend fun updateVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle)
    fun getFuelingRecords(): Flow<List<Fueling>>
    suspend fun getFuelingRecordById(id: Long): Fueling
    fun getLiveFuelingRecordById(id: Long): Flow<Fueling>
    suspend fun insertFueling(fueling: RawFueling): Long
    suspend fun updateFueling(fueling: RawFueling)
    suspend fun deleteFueling(fueling: RawFueling)
}