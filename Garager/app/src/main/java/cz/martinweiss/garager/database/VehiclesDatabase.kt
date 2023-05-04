package cz.martinweiss.garager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.RawFueling
import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Vehicle::class, Manufacturer::class, RawFueling::class], version = 15, exportSchema = true)
abstract class VehiclesDatabase : RoomDatabase() {
    abstract fun VehiclesDao(): VehiclesDao
    abstract fun ManufacturerDao(): ManufacturerDao

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
                            .addCallback(object : RoomDatabase.Callback() { //reinstall app in case of empty entity in dev env
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)

                                    val manufacturers = listOf(
                                        "Acura", "Alfa Romeo", "AM General", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick", "Cadillac", "Chevrolet", "Chrysler", "Daewoo", "Datsun", "Dodge", "Eagle", "Ferrari", "FIAT", "Fisker", "Ford", "Genesis", "Geo", "GMC", "Honda", "HUMMER", "Hyundai", "Infiniti", "Isuzu", "Jaguar", "Jeep", "Kia", "Lamborghini", "Land Rover", "Lexus", "Lincoln", "Lotus", "Maserati", "Maybach", "Mazda", "McLaren", "Mercedes-Benz", "Mercury", "MINI", "Mitsubishi", "Nissan", "Oldsmobile", "Panoz", "Plymouth", "Pontiac", "Porsche", "Ram", "Rolls-Royce", "Saab", "Saturn", "Scion", "Smart", "Sterling", "Subaru", "Suzuki", "Tesla", "Toyota", "Volkswagen", "Volvo"
                                    )

                                    GlobalScope.launch(Dispatchers.IO) {
                                        val dao = getDatabase(context).ManufacturerDao()
                                        manufacturers.forEach {
                                            dao.insertManufacturer(Manufacturer(name = it))
                                        }
                                    }
                                }
                            })
                            .fallbackToDestructiveMigration() // do not push into prod, deletes all data, no need to do schema migration
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}