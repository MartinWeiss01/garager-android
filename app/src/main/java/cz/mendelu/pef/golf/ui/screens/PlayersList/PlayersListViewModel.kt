package cz.mendelu.pef.golf.ui.screens.PlayersList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.golf.architecture.BaseViewModel
import cz.mendelu.pef.golf.database.IPlayersRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlayersListViewModel(private val repository: IPlayersRepository): BaseViewModel() {
    val playersListUIState: MutableState<PlayersListUIState> = mutableStateOf(PlayersListUIState.Default)

    fun loadPlayers() {
        launch {
            repository.getAll().collect() {
                playersListUIState.value = PlayersListUIState.Success(it)
            }
        }
    }
}