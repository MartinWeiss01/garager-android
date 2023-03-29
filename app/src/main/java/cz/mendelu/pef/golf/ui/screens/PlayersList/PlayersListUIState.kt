package cz.mendelu.pef.golf.ui.screens.PlayersList

import cz.mendelu.pef.golf.model.Player

sealed class PlayersListUIState {
    object Default : PlayersListUIState()
    class Success(val players: List<Player>) : PlayersListUIState()
}
