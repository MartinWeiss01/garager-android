package cz.mendelu.pef.golf.ui.screens.PlayersList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cz.mendelu.pef.golf.model.Player
import cz.mendelu.pef.golf.navigation.INavigationRouter
import cz.mendelu.pef.golf.ui.elements.BackArrowScreen
import cz.mendelu.pef.golf.ui.screens.AddPlayer.AddPlayerScreenContent
import org.koin.androidx.compose.getViewModel

@Composable
fun PlayersListScreen(navigation: INavigationRouter, viewModel: PlayersListViewModel = getViewModel()) {
    val players = remember { mutableStateListOf<Player>() }

    viewModel.playersListUIState.value.let {
        when(it) {
            PlayersListUIState.Default -> {
                viewModel.loadPlayers()
            }
            is PlayersListUIState.Success -> {
                players.clear()
                players.addAll(it.players)
            }
        }
    }

    BackArrowScreen(fullScreenContent = true, appBarTitle = "Add item", onBackClick = { navigation.returnBack() }) {
       PlayersListScreenContent(players = players)
    }
}

@Composable
fun PlayersListScreenContent(players: MutableList<Player>) {
    LazyColumn(){
        players.forEach {
            item(key = it.id) {
                PlayerRow(player = it)
            }
        }
    }
}

@Composable
fun PlayerRow(player: Player) {
    Column() {
        Text(text = player.name)
        Text(text = "${player.score}")
    }
}