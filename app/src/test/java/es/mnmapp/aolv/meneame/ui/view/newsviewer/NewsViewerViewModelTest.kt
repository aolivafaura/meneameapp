package es.mnmapp.aolv.meneame.ui.view.newsviewer

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import es.mnmapp.aolv.meneame.utils.Validator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NewsViewerViewModelTest {

    // Bypass main thread on MutableLiveData objects
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var newsViewerViewModel: NewsViewerViewModel
    private val validator = mock<Validator>()

    private val validUrl = "http://www.example.com"
    private val invalidUrl = "invalid url"
    private val title = "title"

    @Before
    fun before() {
        newsViewerViewModel = NewsViewerViewModel(validator)
    }

    @Test
    fun `Given new detail opened, when url is set, then validator is called`() {
        newsViewerViewModel.setUrl(validUrl)
        verify(validator, times(1)).isValidUrl(validUrl)
    }

    @Test
    fun `Given new detail opened, when valid url is set, then url value is assigned`() {
        whenever(validator.isValidUrl(validUrl)).thenReturn(true)

        newsViewerViewModel.setUrl(validUrl)
        assertEquals(validUrl, newsViewerViewModel.url.value)
    }

    @Test
    fun `Given new detail opened, when invalid url is set, then null value is assigned`() {
        whenever(validator.isValidUrl(validUrl)).thenReturn(false)

        newsViewerViewModel.setUrl(invalidUrl)
        assertEquals(null, newsViewerViewModel.url.value)
    }

    @Test
    fun `Given new detail opened, when null url is set, then null value is assigned`() {
        whenever(validator.isValidUrl(validUrl)).thenReturn(false)

        newsViewerViewModel.setUrl(null)
        assertEquals(null, newsViewerViewModel.url.value)
    }

    @Test
    fun `Given new detail opened, when non null title is set, then title value is assigned`() {
        newsViewerViewModel.setTitle(title)
        assertEquals(title, newsViewerViewModel.title.value)
    }

    @Test
    fun `Given new detail opened, when null title is set, then null value is assigned`() {
        newsViewerViewModel.setTitle(null)
        assertEquals("", newsViewerViewModel.title.value)
    }
}