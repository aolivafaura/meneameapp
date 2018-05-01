package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.testing.SimpleIdlingResource
import org.koin.android.ext.android.inject


/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

abstract class BaseActivity : AppCompatActivity() {

    @VisibleForTesting
    val simpleIdlingResource: SimpleIdlingResource by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_base_mobile)
        super.onCreate(savedInstanceState)

        for (viewModel in getViewModels()) {
            viewModel.simpleIdlingResource = simpleIdlingResource
        }
    }

    protected fun setFragment(fragment: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, fragment)
                .commitAllowingStateLoss()
    }

    abstract fun getViewModels(): List<BaseViewModel>
}
