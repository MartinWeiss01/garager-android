package cz.martinweiss.garager.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {
    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToAddEditVehicleScreen(id: Long?) {
        navController.navigate(Destination.AddEditVehicleScreen.route + "/" + id)
    }

    override fun navigateToDetailVehicleScreen(id: Long) {
        navController.navigate(Destination.DetailVehicleScreen.route + "/" + id)
    }

    override fun navigateToSettingsScreen() {
        navController.navigate(Destination.SettingsScreen.route)
    }

    override fun getNavController(): NavController = navController
}