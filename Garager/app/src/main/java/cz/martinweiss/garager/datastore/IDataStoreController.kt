package cz.martinweiss.garager.datastore

const val DATASTORE_MOT_DAYS = "warning_mot"

interface IDataStoreController {
    suspend fun getValueByKey(key: String): String?
    suspend fun updateKey(key: String, value: Any)
}