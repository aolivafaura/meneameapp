package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.mnmapp.aolv.meneame.loggers.AnalitycsLogger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

abstract class BaseFragment : Fragment() {

    private val analitycsLogger by inject<AnalitycsLogger>()

    private val disposables = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        analitycsLogger.setCurrentScreen(activity as BaseActivity, getAnalyticsName())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    @LayoutRes
    abstract fun getFragmentLayout(): Int

    abstract fun getAnalyticsName(): String

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
