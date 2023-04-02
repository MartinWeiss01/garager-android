package cz.martinweiss.garager.navigation

sealed class Destination(val route: String) {
    object VehicleListScreen : Destination(route = "vehicle_list")
}
