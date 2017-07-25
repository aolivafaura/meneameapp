package es.mnmapp.aolv.meneame.ui

import android.arch.lifecycle.LifecycleFragment
import android.content.Context
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by antoniojoseoliva on 22/07/2017.
 */

abstract class BaseFragment : LifecycleFragment() {

    protected val disposables = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()

        disposables.clear()
    }

    override fun onAttach(context : Context?) {
        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()

        disposables.clear()
    }

    /**
     * Add disposables to composite disposables list. All of those disposables will be cleared
     * automatically when the fragment will be detached or destroyed.
     * All your subscriptions may be initialized inside onAttach method. Doing this way, you can
     * be sure all your subscriptions will be on the correct state if the fragment is attached again.
     * Do it to avoid potential memory leaks.
     */
    fun addDisposable(vararg disposable : Disposable) {

        disposables.addAll(*disposable)
    }
}
