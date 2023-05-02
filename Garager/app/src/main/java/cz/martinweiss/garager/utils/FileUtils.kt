package cz.martinweiss.garager.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import java.io.File
import java.net.URLConnection

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

        fun getMimeTypeFromURI(context: Context, uri: Uri): String? {
            //content:// vs file:// problem
            return context.contentResolver.getType(uri)
        }

        fun getMimeTypeFromInternalFile(context: Context, filename: String): String {
            val file = File(context.filesDir, filename)
            val uri = file.toUri()
            return getMimeTypeFromURI(context, uri) ?: "unknown"
        }

        fun getMimeFromFilename(filename: String): String {
            return URLConnection.guessContentTypeFromName(filename)
        }

        fun isInternalFilePDF(filename: String): Boolean {
            val file_ext = getMimeFromFilename(filename)
            return file_ext == "application/pdf"
        }
    }
}