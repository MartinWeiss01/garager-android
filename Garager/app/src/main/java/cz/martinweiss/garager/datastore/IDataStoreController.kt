package cz.martinweiss.garager.datastore

const val DATASTORE_MOT_DAYS = "warning_mot"
enum class VALUE_TYPE {
    STRING, INT, BOOLEAN
}
interface IDataStoreController {
    suspend fun getValueByKey(key: String, type: VALUE_TYPE): Any?
    suspend fun updateKey(key: String, value: Any)
}