package pl.kwiatekmichal.mkdownloader.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import pl.kwiatekmichal.mkdownloader.Constants

class ExtensionTest {

    @Test
    fun testValidateUrlTest() {
        val url = "http://www.orimi.com/pdf-test.pdf"
        val extension = Constants.FileExtension.PDF
        val expected = true
        val actual = url.isValidateUrl(fileExtension = extension)
        assertEquals("Validate URL string 1", expected, actual)
    }

    @Test
    fun testGetFileExtension() {
        val url = "http://www.orimi.com/pdf-test.pdf"
        val expected = Constants.FileExtension.PDF
        val actual = url.getFileExtension()
        assertEquals(expected, actual)
    }
}