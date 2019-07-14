package pl.kwiatekmichal.mkdownloader

import android.app.DownloadManager.Request.*

object Constants {
    const val DEFAULT_NAME = "boring_file"
    const val DEFAULT_TITLE = "boring_title"
    const val DEFAULT_DESCRIPTION = ""

    object HeaderType {
        const val AUTHORIZATION = "Authorization"
        const val ACCEPT = "Accept"
        const val ACCEPT_CHARSET = "Accept-Charset"
        const val ACCEPT_ENCODING = "Accept-Encoding"
        const val ACCEPT_LANGUAGE = "Accept-Language"
        const val CONTENT_TYPE = "Content-Type"
    }

    object NetworkType {
        const val MOBILE = NETWORK_MOBILE
        const val WIFI = NETWORK_MOBILE
    }

    object VisibilityType {
        const val ONLY_WHILE_DOWNLOADING = VISIBILITY_VISIBLE
        const val ALL_THE_TIME = VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        //        const val GONE = VISIBILITY_HIDDEN
        const val missing = VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION
    }

    object Util {
        const val HTTP = "http://"
        const val HTTPS = "https://"
        const val DOT = "."
    }

    object FileExtension {
        const val _7Z = ".7z"
        const val AVI = ".avi"
        const val DOC = ".doc"
        const val DOCX = ".docx"
        const val GIF = ".gif"
        const val ICO = ".ico"
        const val JPEG = ".jpeg"
        const val JPG = ".jpg"
        const val MP3 = ".mp3"
        const val MP4 = ".mp4"
        const val MPEG = ".mpeg"
        const val MPG = ".mpg"
        const val ODP = ".odp"
        const val ODS = ".ods"
        const val ODT = ".odt"
        const val PDF = ".pdf"
        const val PNG = ".png"
        const val PPS = ".pps"
        const val PPT = ".ppt"
        const val PPTX = ".pptx"
        const val RTF = ".rtf"
        const val SVG = ".scg"
        const val XLR = ".xlr"
        const val XLS = ".xls"
        const val XLSX = ".xlsx"

        val LIST = arrayListOf(
            _7Z,
            AVI,
            DOC,
            DOCX,
            GIF,
            ICO,
            JPEG,
            JPG,
            MP3,
            MP4,
            MPEG,
            MPG,
            ODP,
            ODS,
            ODT,
            PDF,
            PNG,
            PPS,
            PPT,
            PPTX,
            RTF,
            SVG,
            XLR,
            XLS,
            XLSX
        )
    }
}