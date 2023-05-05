package cz.martinweiss.garager.model

import androidx.room.*

@Entity(
    tableName = "fueling",
    foreignKeys = [
        ForeignKey(
            entity = Vehicle::class,
            parentColumns = ["id"],
            childColumns = ["vehicle_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RawFueling(
    @ColumnInfo(name = "vehicle_id") var vehicleId: Long,
    @ColumnInfo(name = "date") var date: Long,
) {
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Long? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "price_unit")
    var priceUnit: Double = 0.0

    @ColumnInfo(name = "quantity")
    var quantity: Double = 0.0

    @ColumnInfo(name = "price_sale")
    var priceSale: Double? = null

    @ColumnInfo(name = "specification")
    var specification: String? = null
}

data class Fueling(
    @Embedded val fueling: RawFueling,
    @Relation(parentColumn = "vehicle_id", entityColumn = "id") val vehicle: Vehicle
)