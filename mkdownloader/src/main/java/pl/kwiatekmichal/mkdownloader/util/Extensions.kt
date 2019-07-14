package pl.kwiatekmichal.mkdownloader.util

import pl.kwiatekmichal.mkdownloader.Constants

fun String.isValidateUrl(fileExtension: String = ""): Boolean {
    return when {
        this.isHttpUrl().or(this.isHttpsUrl()) -> {
            return when {
                fileExtension.isBlank() -> true
                else -> this.getFileExtension().isAllowedExtension()
            }
        }
        else -> false
    }
}

private fun String.isHttpUrl(): Boolean {
    return this.startsWith(Constants.Util.HTTP)
}

private fun String.isHttpsUrl(): Boolean {
    return this.startsWith(Constants.Util.HTTPS)
}

private fun String.isAllowedExtension(): Boolean {
    return Constants.FileExtension.LIST.contains(this)
}

fun String.getFileExtension(): String {
    return this.substring(this.lastIndexOf(Constants.Util.DOT))
}