/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.antoniooliva.logger

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class Lgr private constructor(context: Context) : ContextWrapper(context) {

    fun d(message: String?) {
        Timber.d(message)
    }

    fun w(message: String?) {
        Timber.w(message)
    }

    fun i(message: String?) {
        Timber.i(message)
    }

    fun v(message: String?) {
        Timber.v(message)
    }

    fun e(message: String?) {
        Timber.e(message)
    }

    fun logEvent(name: String, params: Map<String, String>?) {
        if (BuildConfig.DEBUG) logDebugEvent(name, params) else logReleaseEvent(name, params)
    }

    private fun logDebugEvent(name: String, params: Map<String, String>?) {
        Timber.i("AnalyticsLogger EVENT LOGGED: $name, $params")
    }

    private fun logReleaseEvent(name: String, params: Map<String, String>?) {
        val bundle = Bundle().apply {
            params?.let {
                for (entry in it.entries) {
                    this.putString(entry.key, entry.value)
                }
            }
        }

        FirebaseAnalytics.getInstance(baseContext).logEvent(name, bundle)
    }

    fun setCurrentScreen(activity: Activity, screen: String) {
        if (BuildConfig.DEBUG) setDebugCurrentScreen(screen) else setReleaseCurrentScreen(activity, screen)
    }

    private fun setDebugCurrentScreen(screen: String) {
        Timber.i("AnalyticsLogger SCREEN SET: $screen")
    }

    private fun setReleaseCurrentScreen(activity: Activity, screen: String) {
        FirebaseAnalytics.getInstance(activity).setCurrentScreen(activity, screen, null)
    }

    class CrashlyticsTree : Timber.Tree() {

        override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
            when (priority) {
                Log.VERBOSE, Log.DEBUG, Log.INFO -> return
            }

            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)

            t?.let { Crashlytics.logException(t) } ?: Crashlytics.logException(Exception(message))
        }

        companion object {
            private const val CRASHLYTICS_KEY_PRIORITY = "priority"
            private const val CRASHLYTICS_KEY_TAG = "tag"
            private const val CRASHLYTICS_KEY_MESSAGE = "message"
        }
    }

    companion object {
        private var selfInstance: Lgr? = null

        @Synchronized
        fun initialize(application: Application) {
            if (selfInstance == null) {
                Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashlyticsTree())

                val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
                Fabric.with(application, Crashlytics.Builder().core(core).build())

                selfInstance = Lgr(application.applicationContext)
            }
        }

        @Synchronized
        fun get(): Lgr =
                selfInstance?.let { it }
                        ?: throw IllegalStateException("Lgr must be initialized first")
    }
}
