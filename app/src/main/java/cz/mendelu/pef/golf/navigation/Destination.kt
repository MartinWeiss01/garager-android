package cz.mendelu.pef.golf.navigation

sealed class Destination(val route: String) {
    object MainScreen : Destination(route = "main_screen")
    object AddPlayerScreen : Destination(route = "add_player")
    object PlayersListScreen : Destination(route = "players_list")
}
