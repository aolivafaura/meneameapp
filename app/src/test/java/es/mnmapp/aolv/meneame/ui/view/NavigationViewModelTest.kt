package es.mnmapp.aolv.meneame.ui.view

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import es.mnmapp.aolv.meneame.entity.NewCellUi
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.Navigation
import org.junit.Before
import org.junit.Test

class NavigationViewModelTest {

    private val navigation = mock<Navigation>()
    private val activity = mock<BaseActivity>()

    private val newUi = NewCellUi(1L, "url", "title", "thumb")

    lateinit var navigationViewModel: NavigationViewModel

    @Before
    fun before() {
        navigationViewModel = NavigationViewModel(navigation)
    }

    @Test
    fun `Given user on app, when navigation to news list is invoked, then navigator is used to navigate`() {
        navigationViewModel.navigateToNewsList(activity)
        verify(navigation, times(1)).navigateToNewsList(activity)
    }

    @Test
    fun `Given user on app, when navigation to new detail, then navigator is used to navigate with new object`() {
        navigationViewModel.navigateToNewsDetail(activity, newUi)
        verify(navigation, times(1)).navigateToNewsDetail(activity, newUi.url, newUi.title)
    }
}