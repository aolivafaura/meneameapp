package es.mnmapp.aolv.meneame.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import es.mnmapp.aolv.meneame.R

/**
 * Created by antoniojoseoliva on 20/07/2017.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_base_mobile)

        super.onCreate(savedInstanceState)
    }

    protected fun setFragment(fragment: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, fragment)
                .commitAllowingStateLoss()
    }
}
