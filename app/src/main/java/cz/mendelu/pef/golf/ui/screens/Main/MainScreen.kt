package cz.mendelu.pef.golf.ui.screens.Main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.mendelu.pef.golf.model.Player
import cz.mendelu.pef.golf.navigation.INavigationRouter
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigation: INavigationRouter, viewModel: MainViewModel = getViewModel()) {
    var player = Player(name = "", score = 0)

    viewModel.mainUIState.value.let {
        when(it) {
            MainUIState.Default -> {
                viewModel.getBestPlayer()
            }
            is MainUIState.Success -> {
                player = it.player
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Main Screen")
            })
        },
        floatingActionButton = {FloatingActionButton(onClick = { navigation.navigateToAddPlayerScreen() }) {}}
    ) {
        MainScreenContent(it, navigation, bestPlayer = player)
    }
}

@Composable
fun MainScreenContent(paddingValues: PaddingValues, navigation: INavigationRouter, bestPlayer: Player) {
    LazyColumn(modifier = Modifier.padding(paddingValues)){
        item {
            Text(text = "Who has best score: ${bestPlayer.name}")
            OutlinedButton(onClick = { navigation.navigateToPlayersListScreen() }) {
                Text(text = "List of words")
            }
        }
    }
}