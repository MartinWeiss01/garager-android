package cz.martinweiss.garager.ui.screens.vehicleList

import cz.martinweiss.garager.datastore.DEFAULT_CURRENCY_VALUE
import cz.martinweiss.garager.datastore.DEFAULT_MOT_WARNING_DAYS_VALUE

class VehicleListData {
    var motDaysWarning: Int = DEFAULT_MOT_WARNING_DAYS_VALUE
    var currency: String = DEFAULT_CURRENCY_VALUE
    var scrollSnapIndex: Int = 0
    var loading: Boolean = true
}