package es.mnmapp.aolv.meneame.ui.view.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.ui.BaseActivity
import es.mnmapp.aolv.meneame.ui.view.main.fragment.MainFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory : MainViewModelFactory

    lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commitAllowingStateLoss()
        }
    }
}
