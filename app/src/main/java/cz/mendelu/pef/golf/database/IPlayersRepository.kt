package cz.mendelu.pef.golf.database

import cz.mendelu.pef.golf.model.Player
import kotlinx.coroutines.flow.Flow

interface IPlayersRepository {
    fun getAll(): Flow<List<Player>>
    suspend fun insert(player: Player): Long
    suspend fun getBestPlayer(): Player
}