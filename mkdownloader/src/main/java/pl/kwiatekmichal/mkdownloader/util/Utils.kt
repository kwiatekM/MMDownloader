package pl.kwiatekmichal.mkdownloader.util

import android.os.Build

object Utils {
    fun isAndroidNougat(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    fun isAndroidMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    fun prepareFileName(name: String, extension: String = "", uri: String): String {
        val name2 = name.trim().replace(" ", "_").replace(".", "_")
        val extension2 = extension.removePrefix(".")
        return when {
            extension.isNotBlank() -> "$name2.$extension2"
            else -> "$name2${uri.getFileExtension()}"
        }
    }

    //todo dodać builder do linków
}