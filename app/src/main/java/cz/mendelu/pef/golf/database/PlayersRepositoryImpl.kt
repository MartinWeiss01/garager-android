package cz.mendelu.pef.golf.database

import cz.mendelu.pef.golf.model.Player
import kotlinx.coroutines.flow.Flow

class PlayersRepositoryImpl(private val dao: PlayersDao): IPlayersRepository {
    override fun getAll(): Flow<List<Player>> {
        return dao.getAll()
    }

    override suspend fun insert(player: Player): Long {
        return dao.insert(player)
    }

    override suspend fun getBestPlayer(): Player {
        return dao.getBestPlayer()
    }

}