package cz.martinweiss.garager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.martinweiss.garager.model.Vehicle

@Database(entities = [Vehicle::class], version = 1, exportSchema = true)
abstract class VehiclesDatabase : RoomDatabase() {
    abstract fun VehiclesDao(): VehiclesDao

    companion object {
        private var INSTANCE: VehiclesDatabase? = null
        fun getDatabase(context: Context): VehiclesDatabase {
            if (INSTANCE == null) {
                synchronized(VehiclesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            VehiclesDatabase::class.java, "vehicles_database"
                        )
                            .fallbackToDestructiveMigration() // do not push into prod, deletes all data, no need to do schema migration
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}