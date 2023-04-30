package cz.martinweiss.garager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "fueling",
    foreignKeys = [
        ForeignKey(
            entity = Vehicle::class,
            parentColumns = ["id"],
            childColumns = ["vehicle_id"]
        )
    ]
)
data class Fueling(
    @ColumnInfo(name = "vehicle_id") var vehicleId: Long,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "price_unit") var priceUnit: Float,
    @ColumnInfo(name = "quantity") var quantity: Float
) {
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Long? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "price_sale")
    var priceSale: Float = 0F
}