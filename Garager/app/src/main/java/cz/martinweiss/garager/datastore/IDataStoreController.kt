package cz.martinweiss.garager.datastore

import cz.martinweiss.garager.model.currencies

const val DATASTORE_MOT_DAYS = "warning_mot"
const val DEFAULT_MOT_WARNING_DAYS_VALUE = 30
const val DATASTORE_CURRENCY = "currency"
var DEFAULT_CURRENCY_VALUE = currencies[0].name
/* TODO Get rid of VALUE_TYPE with DataStoreImpl refactoring */
enum class VALUE_TYPE {
    STRING, INT, BOOLEAN
}
interface IDataStoreController {
    suspend fun getValueByKey(key: String, type: VALUE_TYPE): Any?
    suspend fun updateKey(key: String, value: Any)
}