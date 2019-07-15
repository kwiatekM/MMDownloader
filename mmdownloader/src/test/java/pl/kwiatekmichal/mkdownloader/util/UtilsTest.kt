package pl.kwiatekmichal.mkdownloader.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import pl.kwiatekmichal.mkdownloader.Constants

class UtilsTest {

    @Test
    fun testPrepareFileName() {
        val name = "nazwa_pliku"
        val extension = Constants.FileExtension.PDF
        val uri = "http://www.orimi.com/pdf-test.pdf"
        val expected = "nazwa_pliku.pdf"
        val actual = Utils.prepareFileName(name = name, extension = extension, uri = uri)
        assertEquals("Prepare file name 1", expected, actual)
    }

    @Test
    fun testPrepareFileNameWithoutExtension() {
        val name = "nazwa_pliku"
        val uri = "http://www.orimi.com/pdf-test.pdf"
        val expected = "nazwa_pliku.pdf"
        val actual = Utils.prepareFileName(name = name, uri = uri)
        assertEquals("Prepare file name 2", expected, actual)
    }

    @Test
    fun testPrepareFileNameWithDoubleDots() {
        val name = "nazwa.pliku.png"
        val extension = ".pdf"
        val uri = "http://www.orimi.com/12341234123/receipt"
        val expected = "nazwa_pliku_png.pdf"
        val actual = Utils.prepareFileName(name = name, extension = extension, uri = uri)
        assertEquals("Prepare file name 3", expected, actual)
    }

    @Test
    fun testPrepareFileName2() {
        val name = "Charles Leclerc"
        val uri = "https://sgamingzionm.gamblingzion.com/uploads/2019/07/bet-on-the-2019-british-grand-prix-winner-leclerc.jpg"
        val expected = "Charles_Leclerc.jpg"
        val actual = Utils.prepareFileName(name = name, uri = uri)
        assertEquals("Prepare file name 4", expected, actual)
    }
}