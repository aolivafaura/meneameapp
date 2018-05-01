package es.mnmapp.aolv.meneame.pageobjects

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.v7.widget.RecyclerView
import es.mnmapp.aolv.meneame.R

object ListPageObject {

    fun clickOnNewWithPosition(position: Int) {
        onView(withId(R.id.rvListNews))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    fun scrollToBeginning() {

    }

    fun scrollToEnd() {

    }
}