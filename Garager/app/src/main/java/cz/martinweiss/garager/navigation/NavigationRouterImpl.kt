package cz.martinweiss.garager.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {
    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToAddEditVehicleScreen(id: Long?) {
        navController.navigate(Destination.AddEditVehicleScreen.route + "/" + id)
    }

    override fun navigateToAddEditFuelingScreen(id: Long?) {
        navController.navigate(Destination.AddEditFuelingScreen.route + "/" + id)
    }

    override fun navigateToDetailVehicleScreen(id: Long) {
        navController.navigate(Destination.DetailVehicleScreen.route + "/" + id)
    }

    override fun navigateToGreenCardVehicleScreen(greenCardFilename: String) {
        navController.navigate(Destination.GreenCardVehicleScreen.route + "/" + greenCardFilename)
    }

    override fun navigateToDetailFuelingScreen(id: Long) {
        navController.navigate(Destination.DetailFuelingScreen.route + "/" + id)
    }

    override fun getNavController(): NavController = navController
}