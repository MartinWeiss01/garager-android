package cz.mendelu.pef.golf.ui.screens.Main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.golf.architecture.BaseViewModel
import cz.mendelu.pef.golf.database.IPlayersRepository
import kotlinx.coroutines.launch


class MainViewModel(private val repository: IPlayersRepository) : BaseViewModel(), MainActions {
    val mainUIState: MutableState<MainUIState> = mutableStateOf(MainUIState.Default)

    override fun getBestPlayer() {
        launch {
            val player = repository.getBestPlayer()
            mainUIState.value = MainUIState.Success(player)
        }
    }

}
