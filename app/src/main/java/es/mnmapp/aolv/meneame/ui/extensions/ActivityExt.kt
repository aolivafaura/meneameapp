package es.mnmapp.aolv.meneame.ui.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import es.mnmapp.aolv.meneame.R

fun AppCompatActivity.initFragment(fragment: Fragment,
                                   sharedView: View? = null,
                                   addToBackStack: Boolean? = false) {
    val transaction = this.supportFragmentManager.beginTransaction().add(R.id.container, fragment)

    sharedView?.let {
        transaction.addSharedElement(it, sharedView.transitionName)
    }
    addToBackStack?.let {
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.simpleName)
    }

    transaction.commitAllowingStateLoss()
}

