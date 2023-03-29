package cz.mendelu.pef.golf.ui.screens.Main

import cz.mendelu.pef.golf.model.Player


sealed class MainUIState {
    object Default : MainUIState()
    class Success(val player: Player) : MainUIState()
}