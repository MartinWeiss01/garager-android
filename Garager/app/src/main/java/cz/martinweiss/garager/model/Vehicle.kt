package cz.martinweiss.garager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "vehicles",
    foreignKeys = [
        ForeignKey(
            entity = Manufacturer::class,
            parentColumns = ["id"],
            childColumns = ["manufacturer_id"]
        )
    ]
)
data class Vehicle(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "vin") var vin: String = "",
    @ColumnInfo(name = "license_plate") var licensePlate: String = "",
    @ColumnInfo(name = "manufacturer_id") var manufacturer: Long? = null
) {
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null
}

/*
STK date
*/
