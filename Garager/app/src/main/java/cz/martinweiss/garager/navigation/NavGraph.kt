package cz.martinweiss.garager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.martinweiss.garager.ui.screens.settings.SettingsScreen
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.AddEditVehicleScreen
import cz.martinweiss.garager.ui.screens.vehicleDetail.DetailVehicleScreen
import cz.martinweiss.garager.ui.screens.vehicleGreenCard.GreenCardVehicleScreen
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

        composable(
            route = Destination.AddEditVehicleScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            val id = it.arguments?.getLong("id")
            AddEditVehicleScreen(navigation, id = if (id != -1L) id else null)
        }

        composable(
            route = Destination.DetailVehicleScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            val id = it.arguments?.getLong("id")
            DetailVehicleScreen(navigation, id = id ?: -1L)
        }

        composable(Destination.SettingsScreen.route) {
            SettingsScreen(navigation = navigation)
        }

        composable(
            route = Destination.GreenCardVehicleScreen.route + "/{greenCardFilename}",
            arguments = listOf(
                navArgument("greenCardFilename") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val greenCardFilename = it.arguments?.getString("greenCardFilename")
            GreenCardVehicleScreen(navigation = navigation, greenCardFileName = greenCardFilename!!)
        }
    }
}