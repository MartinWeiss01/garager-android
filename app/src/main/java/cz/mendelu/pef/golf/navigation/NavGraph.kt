package cz.mendelu.pef.golf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.mendelu.pef.golf.ui.screens.AddPlayer.AddPlayerScreen
import cz.mendelu.pef.golf.ui.screens.Main.MainScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember { NavigationRouterImpl(navController) },
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.MainScreen.route) {
            MainScreen(navigation)
        }

        composable(Destination.AddPlayerScreen.route) {
            AddPlayerScreen(navigation)
        }
    }
}