package pl.kwiatekmichal.mkdownloader

abstract class MMDownloaderException(override val message: String) : RuntimeException()

class MMDownloaderUriException(message: String) : MMDownloaderException(message = message)
class MMDownloaderEmptyRequestException(message: String) : MMDownloaderException(message = message)