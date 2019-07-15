package pl.kwiatekmichal.mkdownloader.notification

import pl.kwiatekmichal.mkdownloader.Constants

data class Notification(
    val title: String = Constants.DEFAULT_TITLE,
    val description: String = Constants.DEFAULT_DESCRIPTION,
    val visibility: Int = Constants.VisibilityType.ONLY_WHILE_DOWNLOADING
)