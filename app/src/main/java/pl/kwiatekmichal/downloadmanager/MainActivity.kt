package pl.kwiatekmichal.downloadmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import pl.kwiatekmichal.mkdownloader.Constants
import pl.kwiatekmichal.mkdownloader.MMDownloader
import pl.kwiatekmichal.mkdownloader.MMDownloaderException
import pl.kwiatekmichal.mkdownloader.MMRequest
import pl.kwiatekmichal.mkdownloader.notification.Notification

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var button: Button
    private var onMMDownloaderListener: MMDownloader.OnMMDownloaderListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        initListeners()

        button.setOnClickListener {
            if (MMDownloader.checkRequiredPermissions(this)) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MMDownloader.WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST_CODE
                )
            } else {
                startDownloader()
            }
        }

//        val start = "Kwiatek: "
//        val end = "24.09.1992"
//        val boldFont = ResourcesCompat.getFont(this, R.font.roboto_bold)
//
//        textView.text = MKSpannatics.make(
//            MKSpannaticItem(
//                start,
//                MKCustomFontSpan(
//                    color = ContextCompat.getColor(this, R.color.mk_red),
//                    size = 22f * resources.displayMetrics.scaledDensity,
//                    font = boldFont
//                )
//            ),
//            MKSpannaticItem(
//                end,
//                MKCustomFontSpan(
//                    color = ContextCompat.getColor(this, R.color.mk_blue),
//                    size = 24f * resources.displayMetrics.scaledDensity,
//                    font = boldFont
//                )
//            )
//        )
    }

    private fun initListeners() {
        initRequestListener()
    }

    private fun initRequestListener() {
        val context = this
        onMMDownloaderListener = object : MMDownloader.OnMMDownloaderListener {
            override fun onError(error: MMDownloaderException) {
                Toast.makeText(context, error.message, LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MMDownloader.WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloader()
            }
        }
    }


    private fun startDownloader() {
        val context = this
        MMDownloader.Builder()
            .with(context)
            .addRequest(
                MMRequest.Builder()
                    .create(context)
                    .forName("POBRANY PLIK")
//                    .fileExtension(Constants.FileExtension.PDF)
                    .from("http://www.orimi.com/pdf-test.pdf")//"https://www.androidtutorialpoint.com/wp-content/uploads/2016/09/Beauty.jpg")//
                    .withNotification(
                        Notification(
                            title = "Tytuł",
                            description = "opis notyfikacji",
                            visibility = Constants.VisibilityType.ALL_THE_TIME
                        )
                    )
                    .inDocumentsDirecoryDestination()
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make(),
                MMRequest.Builder()
                    .create(context)
                    .forName("Charles Leclerc")
                    .from("https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg")
                    .inDownloadsDestinationDestination()
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make(),
                MMRequest.Builder()
                    .create(context)
                    .forName("Charles Leclerc 2222")
                    .withNotification(
                        Notification(
                            title = "Mój jest ten kawałek podłogi"
                        )
                    )
                    .from("https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg")
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make(),
                MMRequest.Builder()
                    .create(context)
                    .forName("Charles Leclerc 2222")
                    .withNotification(
                        Notification(
                            title = "Mój jest ten kawałek podłogi"
                        )
                    )
                    .from("https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg")
                    .inPicturesDirectoryDestination()
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make(),
                MMRequest.Builder()
                    .create(context)
                    .forName("Charles Leclerc 2222")
                    .withNotification(
                        Notification(
                            title = "Mój jest ten kawałek podłogi"
                        )
                    )
                    .from("https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg")
                    .inMoviesDirectoryDestination()
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make(),
                MMRequest.Builder()
                    .create(context)
                    .forName("Charles Leclerc 2222")
                    .inMusicsDirecoryDestination()
                    .from("https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg")
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make(),
                MMRequest.Builder()
                    .create(context)
                    .forName("Charles Leclerc 2222")
                    .inPodcastsDirectoryDestination()
                    .from("https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg")
                    .addOnMkDownloaderListener(onMMDownloaderListener)
                    .make()
            )
            .addOnChangeEnqueueListener(listener = object : MMDownloader.OnChangeEnqueueListener {
                override fun onNewDownloadId(downloadId: Long) {
                    Log.e(TAG, "Identyfikator: $downloadId")
                }

                override fun onNewListDownloadedId(downloadIdList: List<Long>) {
                    Log.e(TAG, "Identyfikatory: $downloadIdList")
                }

                override fun onError(error: MMDownloaderException) {
                    Toast.makeText(context, error.message, LENGTH_SHORT).show()
                }
            })
            .start()
    }
}
