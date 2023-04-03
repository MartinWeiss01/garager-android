package cz.martinweiss.garager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "vin") var vin: String = "",
    @ColumnInfo(name = "license_plate") var licensePlate: String = ""
) {
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null
}

/*
ManufacturerId
STK date
*/
