package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

abstract class BaseFragment : Fragment() {

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    @LayoutRes
    abstract fun getFragmentLayout(): Int

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
