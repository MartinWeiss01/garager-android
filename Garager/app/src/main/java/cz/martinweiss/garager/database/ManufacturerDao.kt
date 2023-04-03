package cz.martinweiss.garager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import cz.martinweiss.garager.model.Manufacturer

@Dao
interface ManufacturerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManufacturer(manufacturer: Manufacturer)
}