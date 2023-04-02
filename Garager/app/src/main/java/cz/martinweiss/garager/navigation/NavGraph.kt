package cz.martinweiss.garager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.martinweiss.garager.ui.screens.vehicleList.VehicleListScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.VehicleListScreen.route) {
            VehicleListScreen(navigation)
        }
    }
}