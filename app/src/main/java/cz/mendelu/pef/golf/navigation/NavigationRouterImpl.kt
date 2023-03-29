package cz.mendelu.pef.golf.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {
    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToAddPlayerScreen() {
        navController.navigate(Destination.AddPlayerScreen.route)
    }

    override fun navigateToPlayersListScreen() {
        navController.navigate(Destination.PlayersListScreen.route)
    }

    override fun getNavConotroller(): NavController = navController

}