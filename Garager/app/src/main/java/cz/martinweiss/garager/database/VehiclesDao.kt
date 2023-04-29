package cz.martinweiss.garager.database

import androidx.room.*
import cz.martinweiss.garager.model.Manufacturer
import kotlinx.coroutines.flow.Flow
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.model.VehicleWithManufacturer

@Dao
interface VehiclesDao {
  @Query("SELECT * FROM vehicles")
  fun getVehicles(): Flow<List<Vehicle>>

  @Query("SELECT * FROM vehicles WHERE id = :id")
  fun getVehicleById(id: Long): Flow<VehicleWithManufacturer>

  @Query("SELECT * FROM vehicles WHERE id = :id")
  suspend fun getVehicleWithManufacturerById(id: Long): VehicleWithManufacturer

  @Query("SELECT * FROM manufacturers")
  suspend fun getManufacturers(): List<Manufacturer>

  @Insert
  suspend fun insertVehicle(vehicle: Vehicle): Long

  @Update
  suspend fun updateVehicle(vehicle: Vehicle)
  @Delete
  suspend fun deleteVehicle(vehicle: Vehicle)
}