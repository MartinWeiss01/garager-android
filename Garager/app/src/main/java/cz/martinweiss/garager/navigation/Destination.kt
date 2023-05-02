package cz.martinweiss.garager.navigation

import cz.martinweiss.garager.R

sealed class Destination(val route: String, val title: Int? = null, val icon: Int?  = null, val iconActive: Int? = null) {
    object VehicleListScreen : Destination(route = "vehicle_list", R.string.navigation_item_list, R.drawable.icon_car, R.drawable.icon_car_filled)
    object FuelListScreen : Destination(route = "fuel_list", R.string.navigation_item_fuelings, R.drawable.icon_gas, R.drawable.icon_gas_filled)
    object SettingsScreen : Destination(route = "settings", R.string.navigation_item_settings, R.drawable.icon_settings, R.drawable.icon_settings_filled)
    object AddEditVehicleScreen : Destination(route = "add_edit_vehicle")
    object AddEditFuelingScreen : Destination(route = "add_edit_fueling")
    object DetailVehicleScreen : Destination(route = "detail_vehicle")
    object GreenCardVehicleScreen : Destination(route = "green_card_vehicle")
    object DetailFuelingScreen : Destination(route = "detail_fueling")
}

/*
    BOTTOM_NAVBAR_LINKS should contain only objects with all parameters,
    otherwise the application may crash
*/
val BOTTOM_NAVBAR_LINKS = listOf(
    Destination.VehicleListScreen,
    Destination.FuelListScreen,
    Destination.SettingsScreen
)