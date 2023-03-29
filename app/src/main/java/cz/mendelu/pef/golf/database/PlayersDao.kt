package cz.mendelu.pef.golf.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.mendelu.pef.golf.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayersDao {

    @Query("SELECT * FROM players")
    fun getAll(): Flow<List<Player>>

    @Insert
    suspend fun insert(player: Player): Long

    @Query("SELECT * FROM players ORDER BY score ASC LIMIT 1")
    suspend fun getBestPlayer(): Player
}