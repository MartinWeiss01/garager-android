package cz.martinweiss.garager.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

class FileUtils {
    companion object {
        fun getFileNameFromURI(context: Context, uri: Uri?): String? {
            if(uri != null) {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor?.moveToFirst()
                val fileName = cursor?.getString(nameIndex!!)
                cursor?.close()
                return fileName
            }
            return null
        }

        fun copyExternalFile(context: Context, externalUri: Uri): String? {
            val inputStream = context.contentResolver.openInputStream(externalUri)
            val fileName = getFileNameFromURI(context, externalUri)
            if(fileName != null) {
                val file = File(context.filesDir, fileName)
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
            return fileName
        }

        fun deleteInternalFile(context: Context, filename: String): Boolean {
            val file = File(context.filesDir, filename)
            return file.delete()
        }
    }
}