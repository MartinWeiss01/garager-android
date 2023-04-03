package cz.martinweiss.garager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.martinweiss.garager.model.Manufacturer
import kotlinx.coroutines.flow.Flow
import cz.martinweiss.garager.model.Vehicle

@Dao
interface VehiclesDao {
  @Query("SELECT * FROM vehicles")
  fun getVehicles(): Flow<List<Vehicle>>

  @Query("SELECT * FROM vehicles WHERE id = :id")
  suspend fun getVehicleById(id: Long): Vehicle

  @Query("SELECT * FROM manufacturers")
  fun getManufacturers(): Flow<List<Manufacturer>>

  @Insert
  suspend fun insertVehicle(vehicle: Vehicle): Long

  @Query("UPDATE vehicles SET name = :vehicleName, vin = :vehicleVin, license_plate = :vehicleLicensePlate, manufacturer_id = :vehicleManufacturerId WHERE id = :vehicleId")
  suspend fun updateVehicle(vehicleId: Long, vehicleName: String, vehicleVin: String, vehicleLicensePlate: String, vehicleManufacturerId: Long?)
}