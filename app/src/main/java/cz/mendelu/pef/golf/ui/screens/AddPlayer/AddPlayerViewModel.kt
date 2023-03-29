package cz.mendelu.pef.golf.ui.screens.AddPlayer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.golf.architecture.BaseViewModel
import cz.mendelu.pef.golf.database.IPlayersRepository
import cz.mendelu.pef.golf.model.Player
import kotlinx.coroutines.launch

class AddPlayerViewModel(private val repository: IPlayersRepository) : BaseViewModel(), AddPlayerActions {
    val addPlayerUIState: MutableState<AddPlayerUIState> = mutableStateOf(AddPlayerUIState.Default)

    override fun savePlayer(name: String, score: Int) {
        launch {
            val id = repository.insert(Player(name, score))
            if(id > 0) {
                addPlayerUIState.value = AddPlayerUIState.PlayerSaved
            }
        }
    }

}