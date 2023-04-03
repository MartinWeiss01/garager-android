package cz.martinweiss.garager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manufacturers")
data class Manufacturer(
    @ColumnInfo val name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null
}