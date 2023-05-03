package cz.martinweiss.garager.datastore

const val DATASTORE_MOT_DAYS = "warning_mot"
const val DEFAULT_MOT_WARNING_DAYS_VALUE = 30
enum class VALUE_TYPE {
    STRING, INT, BOOLEAN
}
interface IDataStoreController {
    suspend fun getValueByKey(key: String, type: VALUE_TYPE): Any?
    suspend fun updateKey(key: String, value: Any)
}