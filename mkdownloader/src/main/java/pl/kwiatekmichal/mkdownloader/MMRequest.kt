package pl.kwiatekmichal.mkdownloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import pl.kwiatekmichal.mkdownloader.header.Header
import pl.kwiatekmichal.mkdownloader.notification.Notification
import pl.kwiatekmichal.mkdownloader.util.Utils
import pl.kwiatekmichal.mkdownloader.util.isValidateUrl

class MMRequest {

    class Builder {
        private var context: Context? = null
        private var name: String = Constants.DEFAULT_NAME
        private var fileExtension: String = ""
        private var uri: Uri = Uri.EMPTY
        private var headers: MutableMap<String, String> = mutableMapOf()
        private var networkType: Int? = null
        private var isAllowed: Boolean? = null
        private var isRoaming: Boolean? = null
        private var notification: Notification? = null
        private var isDocumentsDirectoryDestination: Boolean = false
        private var isMusicsDirectoryDestination: Boolean = false
        private var isMoviesDirectoryDestination: Boolean = false
        private var isPicturesDirectoryDestination: Boolean = false
        private var isPodcastsDirectoryDestination: Boolean = false
        private var isDownloadsDirectoryDestination: Boolean = false
        private var onMMDownloaderListener: MMDownloader.OnMMDownloaderListener? = null

        fun create(context: Context): Builder {
            this.context = context
            return this
        }

        fun forName(name: String): Builder {
            this.name = name
            return this
        }

        fun fileExtension(fileExtension: String): Builder {
            this.fileExtension = fileExtension
            return this
        }

        fun from(url: String): Builder {
            return from(Uri.parse(url))
        }

        fun from(uri: Uri): Builder {
            this.uri = uri
            return this
        }

        fun withHeader(vararg headers: Header): Builder {
            headers.forEach {
                this.headers[it.header] = it.value
            }
            return this
        }

        fun setAllowedNetworkTypes(type: Int): Builder {
            this.networkType = type
            return this
        }

        fun setAllowedOverMetered(isAllowed: Boolean): Builder {
            this.isAllowed = isAllowed
            return this
        }

        fun setAllowedOverRoaming(isRoaming: Boolean): Builder {
            this.isRoaming = isRoaming
            return this
        }

        fun withNotification(notification: Notification? = null): Builder {
            this.notification = notification
            return this
        }

        fun inDocumentsDirecoryDestination(): Builder {
            this.isMoviesDirectoryDestination = false
            this.isMusicsDirectoryDestination = false
            this.isPodcastsDirectoryDestination = false
            this.isPicturesDirectoryDestination = false
            this.isDownloadsDirectoryDestination = false
            this.isDocumentsDirectoryDestination = true
            return this
        }

        fun inMusicsDirecoryDestination(): Builder {
            this.isMoviesDirectoryDestination = false
            this.isPodcastsDirectoryDestination = false
            this.isPicturesDirectoryDestination = false
            this.isDownloadsDirectoryDestination = false
            this.isDocumentsDirectoryDestination = false
            this.isMusicsDirectoryDestination = true
            return this
        }

        fun inMoviesDirectoryDestination(): Builder {
            this.isMusicsDirectoryDestination = false
            this.isPodcastsDirectoryDestination = false
            this.isPicturesDirectoryDestination = false
            this.isDownloadsDirectoryDestination = false
            this.isDocumentsDirectoryDestination = false
            this.isMoviesDirectoryDestination = true
            return this
        }

        fun inPicturesDirectoryDestination(): Builder {
            this.isMusicsDirectoryDestination = false
            this.isPodcastsDirectoryDestination = false
            this.isDownloadsDirectoryDestination = false
            this.isDocumentsDirectoryDestination = false
            this.isMoviesDirectoryDestination = false
            this.isPicturesDirectoryDestination = true
            return this
        }

        fun inPodcastsDirectoryDestination(): Builder {
            this.isMusicsDirectoryDestination = false
            this.isPicturesDirectoryDestination = false
            this.isDownloadsDirectoryDestination = false
            this.isDocumentsDirectoryDestination = false
            this.isMoviesDirectoryDestination = false
            this.isPodcastsDirectoryDestination = true
            return this
        }

        fun inDownloadsDestinationDestination(): Builder {
            this.isMusicsDirectoryDestination = false
            this.isPicturesDirectoryDestination = false
            this.isDocumentsDirectoryDestination = false
            this.isMoviesDirectoryDestination = false
            this.isPodcastsDirectoryDestination = false
            this.isDownloadsDirectoryDestination = true
            return this
        }

        fun addOnMkDownloaderListener(onMMDownloaderListener: MMDownloader.OnMMDownloaderListener?): Builder {
            this.onMMDownloaderListener = onMMDownloaderListener
            return this
        }

        fun make(): DownloadManager.Request? {
            return when {
                uri.toString().isValidateUrl(this.fileExtension) -> build()
                else -> {
                    onMMDownloaderListener?.onError(MMDownloaderUriException("$uri is not valid uri"))
                    null
                }
            }
        }

        private fun build(): DownloadManager.Request {
            val builder = DownloadManager.Request(uri)
            if (headers.isNotEmpty()) {
                when {
                    Utils.isAndroidNougat() -> headers.forEach { (header, value) ->
                        builder.addRequestHeader(header, value)
                    }
                    else -> for ((header, value) in headers) {
                        builder.addRequestHeader(header, value)
                    }
                }
            }
            networkType?.let {
                builder.setAllowedNetworkTypes(it)
            }
            isAllowed?.let {
                builder.setAllowedOverMetered(it)
            }
            isRoaming?.let {
                builder.setAllowedOverRoaming(it)
            }
            notification?.let {
                builder.setTitle(it.title)
                if (it.description.isNotBlank()) {
                    builder.setDescription(it.description)
                }
                builder.setNotificationVisibility(it.visibility)
            }
            if (isDocumentsDirectoryDestination) {
                toDocumentsDirectory(builder)
            }
            if (isMusicsDirectoryDestination) {
                toMusicsDirectory(builder)
            }
            if (isMoviesDirectoryDestination) {
                toMoviesDirectory(builder)
            }
            if (isPicturesDirectoryDestination) {
                toPicturesDirectory(builder)
            }
            if (isPodcastsDirectoryDestination) {
                toPodcastsDirectory(builder)
            }
            if (isDownloadsDirectoryDestination) {
                toDownloadDirectory(builder)
            }
            if (!isDocumentsDirectoryDestination && !isMusicsDirectoryDestination && !isMoviesDirectoryDestination
                && !isPicturesDirectoryDestination && !isPodcastsDirectoryDestination && !isDownloadsDirectoryDestination
            ) {
                toDownloadDirectory(builder)
            }
            return builder
        }

        private fun toDocumentsDirectory(builder: DownloadManager.Request) {
            prepareDownloadDestination(builder, Environment.DIRECTORY_DOCUMENTS)
        }

        private fun toMusicsDirectory(builder: DownloadManager.Request) {
            prepareDownloadDestination(builder, Environment.DIRECTORY_MUSIC)
        }

        private fun toMoviesDirectory(builder: DownloadManager.Request) {
            prepareDownloadDestination(builder, Environment.DIRECTORY_MOVIES)
        }

        private fun toPicturesDirectory(builder: DownloadManager.Request) {
            prepareDownloadDestination(builder, Environment.DIRECTORY_PICTURES)
        }

        private fun toPodcastsDirectory(builder: DownloadManager.Request) {
            prepareDownloadDestination(builder, Environment.DIRECTORY_PODCASTS)
        }

        private fun toDownloadDirectory(builder: DownloadManager.Request) {
            prepareDownloadDestination(builder, Environment.DIRECTORY_DOWNLOADS)
        }

        private fun prepareDownloadDestination(builder: DownloadManager.Request, destination: String) {
            Environment.getExternalStoragePublicDirectory(destination).mkdirs()
            builder.setDestinationInExternalPublicDir(
                destination,
                Utils.prepareFileName(this.name, this.fileExtension, this.uri.toString())
            )
        }
    }
}