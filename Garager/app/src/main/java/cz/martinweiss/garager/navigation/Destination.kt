package cz.martinweiss.garager.navigation

import android.content.res.Resources
import android.graphics.drawable.Drawable
import cz.martinweiss.garager.R

sealed class Destination(val route: String, val title: Int?, val icon: Int?, val iconActive: Int?) {
    object VehicleListScreen : Destination(route = "vehicle_list", R.string.navigation_item_list, R.drawable.icon_car, R.drawable.icon_car_filled)
    object FuelListScreen : Destination(route = "fuel_list", R.string.navigation_item_fuelings, R.drawable.icon_gas, R.drawable.icon_gas_filled)
    object AddEditVehicleScreen : Destination(route = "add_edit_vehicle", null, null, null)
    object AddEditFuelingScreen : Destination(route = "add_edit_fueling", null, null, null)
    object DetailVehicleScreen : Destination(route = "detail_vehicle", null, null, null)
    object GreenCardVehicleScreen : Destination(route = "green_card_vehicle", null, null, null)
    object DetailFuelingScreen : Destination(route = "detail_fueling", null, null, null)
    object SettingsScreen : Destination(route = "settings", R.string.navigation_item_settings, R.drawable.icon_settings, R.drawable.icon_settings_filled)
}
