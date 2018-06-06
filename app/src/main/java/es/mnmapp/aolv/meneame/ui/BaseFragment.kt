package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antoniooliva.logger.Lgr
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

abstract class BaseFragment : Fragment() {

    // Fields -----
    private val disposables = CompositeDisposable()

    // Fragment overrides -----
    override fun onResume() {
        super.onResume()
        Lgr.get().setCurrentScreen(activity!!, getAnalyticsName())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    // Abstract methods -----
    @LayoutRes
    abstract fun getFragmentLayout(): Int

    abstract fun getAnalyticsName(): String

    // Protected methods -----
    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
