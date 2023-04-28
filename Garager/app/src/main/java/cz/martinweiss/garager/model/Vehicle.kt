package cz.martinweiss.garager.model

import androidx.room.*

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
    @ColumnInfo(name = "name") var name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null

    @ColumnInfo(name = "vin")
    var vin: String? = null

    @ColumnInfo(name = "license_plate")
    var licensePlate: String? = null

    @ColumnInfo(name = "manufacturer_id")
    var manufacturer: Long? = null

    @ColumnInfo(name = "mot_date")
    var motDate: Long? = null

    @ColumnInfo(name = "internal_filename")
    var greenCardFilename: String? = null
}

data class VehicleWithManufacturer(
    @Embedded val vehicle: Vehicle,
    @Relation(parentColumn = "manufacturer_id", entityColumn = "id") val manufacturer: Manufacturer?
)