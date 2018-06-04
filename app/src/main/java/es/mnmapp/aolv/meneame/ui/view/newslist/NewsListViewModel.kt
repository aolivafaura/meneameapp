package es.mnmapp.aolv.meneame.ui.view.newslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.mnmapp.aolv.domain.entity.New
import es.mnmapp.aolv.domain.usecase.GetNews
import es.mnmapp.aolv.meneame.entity.NewCellUi
import es.mnmapp.aolv.meneame.entity.fromNewToNewCellUi

/**
 * Created by antonio on 2/24/18.
 */

class NewsListViewModel(private val getNews: GetNews) : ViewModel() {

	// Fields -----
	val news = MutableLiveData<List<NewCellUi>>()
	val state = MutableLiveData<ViewState>()

	// ViewModel overrides -----
	override fun onCleared() {
		super.onCleared()
		getNews.clear()
	}

	// Class methods -----
	fun fetchNews() {
		val successHandler: ((List<New>) -> Unit) = {
			state.value = ViewState.Idle
			news.value = it.map { fromNewToNewCellUi(it) }
		}
		val errorHandler: ((Throwable) -> Unit) = {
			state.value = ViewState.Idle
		}

		state.value = ViewState.Refreshing
		getNews.execute(Unit, successHandler, errorHandler)
	}

	// Inner classes -----
	enum class ViewState {
		Idle,
		Refreshing
	}
}
