package cz.martinweiss.garager.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun returnBack()
    fun navigateToAddEditVehicleScreen(id: Long?)
    fun navigateToDetailVehicleScreen(id: Long)
    fun navigateToGreenCardVehicleScreen(greenCardFilename: String)
    fun navigateToSettingsScreen()
    fun getNavController(): NavController
}