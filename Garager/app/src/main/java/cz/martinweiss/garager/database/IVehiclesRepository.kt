package cz.martinweiss.garager.database

import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface IVehiclesRepository {
    fun getVehicles(): Flow<List<Vehicle>>
    suspend fun insertVehicle(vehicle: Vehicle): Long
}