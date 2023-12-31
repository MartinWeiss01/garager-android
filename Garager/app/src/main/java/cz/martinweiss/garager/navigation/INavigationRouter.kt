package cz.martinweiss.garager.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun returnBack()
    fun navigateToAddEditVehicleScreen(id: Long?)
    fun navigateToAddEditFuelingScreen(id: Long?)
    fun navigateToDetailVehicleScreen(id: Long)
    fun navigateToGreenCardVehicleScreen(greenCardFilename: String)
    fun navigateToDetailFuelingScreen(id: Long)
    fun getNavController(): NavController
}