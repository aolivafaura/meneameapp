package es.mnmapp.aolv.meneame.ui

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

abstract class BaseActivity : AppCompatActivity(), HasFragmentInjector {

    @Inject lateinit var fragmentDispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    protected val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState : Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.clear()
    }

    /**
     * Add disposable to composite disposables list. All of those disposables will be cleared
     * automatically when the activity will be destroyed.
     * Do it to avoid potential memory leaks.
     */
    fun addDisposable(vararg disposable : Disposable) {

        disposables.addAll(*disposable)
    }

    override fun fragmentInjector() : AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
