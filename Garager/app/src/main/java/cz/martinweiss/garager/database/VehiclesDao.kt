package cz.martinweiss.garager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import cz.martinweiss.garager.model.Vehicle

@Dao
interface VehiclesDao {
  @Query("SELECT * FROM vehicles")
  fun getVehicles(): Flow<List<Vehicle>>

  @Insert
  suspend fun insertVehicle(vehicle: Vehicle): Long
}