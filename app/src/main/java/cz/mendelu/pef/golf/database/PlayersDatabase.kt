package cz.mendelu.pef.golf.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.mendelu.pef.golf.model.Player

@Database(entities = [Player::class], version = 1, exportSchema = true)
abstract class PlayersDatabase : RoomDatabase() {
    abstract fun PlayersDao(): PlayersDao

    companion object {
        private var INSTANCE: PlayersDatabase? = null
        fun getDatabase(context: Context): PlayersDatabase {
            if (INSTANCE == null) {
                synchronized(PlayersDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PlayersDatabase::class.java, "players_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}