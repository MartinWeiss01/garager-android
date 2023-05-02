package cz.martinweiss.garager.database

import androidx.room.*
import cz.martinweiss.garager.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VehiclesDao {
  @Query("SELECT * FROM vehicles")
  fun getVehicles(): Flow<List<Vehicle>>

  @Query("SELECT * FROM vehicles")
  suspend fun getAvailableVehicles(): List<Vehicle>

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

  @Query("SELECT * FROM fueling ORDER BY id DESC")
  fun getFuelingRecords(): Flow<List<Fueling>>

  @Query("SELECT * FROM fueling WHERE id = :id")
  suspend fun getFuelingRecordById(id: Long): Fueling

  @Query("SELECT * FROM fueling WHERE id = :id")
  fun getLiveFuelingRecordById(id: Long): Flow<Fueling>

  @Insert
  suspend fun insertFueling(fueling: RawFueling): Long

  @Update
  suspend fun updateFueling(fueling: RawFueling)

  @Delete
  suspend fun deleteFueling(fueling: RawFueling)
}