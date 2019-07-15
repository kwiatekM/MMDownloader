package pl.kwiatekmichal.mkdownloader

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import pl.kwiatekmichal.mkdownloader.util.Utils


class MMDownloader {
    companion object {
        const val WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST_CODE = 1337

        fun checkRequiredPermissions(context: Context): Boolean {
            return Utils.isAndroidMarshmallow()
                    && hasPermissionInManifest(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        }

        private fun hasPermissionInManifest(context: Context, permissionName: String): Boolean {
            try {
                val packageInfo =
                    context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
                val declaredPermissions = packageInfo.requestedPermissions
                if (declaredPermissions != null && declaredPermissions.isNotEmpty()) {
                    for (p in declaredPermissions) {
                        if (p.equals(permissionName, ignoreCase = true)) {
                            return true
                        }
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return false
        }

        fun cancelDownloading(context: Context, downloadId: Long) {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.remove(downloadId)
        }

        fun cancelDownloading(context: Context, downloadIdList: List<Long>) {
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadIdList.forEach {
                downloadManager.remove(it)
            }
        }
    }

    class Builder {
        private var context: Context? = null
        private var listener: OnChangeEnqueueListener? = null
        private var requestsList: MutableList<DownloadManager.Request> = mutableListOf()

        fun with(context: Context): Builder {
            this.context = context
            return this
        }

        fun addRequest(request: DownloadManager.Request?): Builder {
            when {
                request != null -> this.requestsList.add(request)
                else -> this.listener?.onError(error = MMDownloaderEmptyRequestException("Request is empty."))
            }
            return this
        }

        fun addRequest(vararg requests: DownloadManager.Request?): Builder {
            if (requests.isNotEmpty()) {
                requests.forEach {
                    when {
                        it != null -> this.requestsList.add(it)
                        else -> this.listener?.onError(error = MMDownloaderEmptyRequestException("Request is empty."))
                    }
                }
            }
            return this
        }

        fun addRequest(requests: List<DownloadManager.Request?>): Builder {
            if (requests.isNotEmpty()) {
                requests.forEach {
                    when {
                        it != null -> this.requestsList.add(it)
                        else -> this.listener?.onError(error = MMDownloaderEmptyRequestException("Request is empty."))
                    }
                }
            }
            return this
        }

        fun addOnChangeEnqueueListener(listener: OnChangeEnqueueListener): Builder {
            this.listener = listener
            return this
        }

        fun start() {
            createManager()
        }

        private fun createManager() {
            val downloadManager = this.context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadIdList = mutableListOf<Long>()
            this.requestsList.forEach {
                val downloadId = downloadManager.enqueue(it)
                downloadIdList.add(downloadId)
                this.listener?.onNewDownloadId(downloadId = downloadId)
            }
            this.listener?.onNewListDownloadedId(downloadIdList = downloadIdList)
        }
    }

    interface OnChangeEnqueueListener {
        fun onNewDownloadId(downloadId: Long)
        fun onNewListDownloadedId(downloadIdList: List<Long>)
        fun onError(error: MMDownloaderException)
    }

    interface OnMMDownloaderListener {
        fun onError(error: MMDownloaderException)
    }
}