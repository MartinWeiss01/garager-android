package cz.martinweiss.garager.navigation

import android.content.res.Resources
import android.graphics.drawable.Drawable
import cz.martinweiss.garager.R

sealed class Destination(val route: String, val title: Int, val icon: Int, val iconActive: Int) {
    object VehicleListScreen : Destination(route = "vehicle_list", R.string.navigation_item_list, R.drawable.icon_car, R.drawable.icon_car_filled)
}
