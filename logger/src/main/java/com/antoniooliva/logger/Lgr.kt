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

class Lgr private constructor(context: Context) : ContextWrapper(context) {

    fun d(message: String?) {
        ServiceLocator.provideTimber().d(message)
    }

    fun w(message: String?) {
        ServiceLocator.provideTimber().w(message)
    }

    fun i(message: String?) {
        ServiceLocator.provideTimber().i(message)
    }

    fun v(message: String?) {
        ServiceLocator.provideTimber().v(message)
    }

    fun e(message: String?) {
        ServiceLocator.provideTimber().e(message)
    }

    fun logEvent(name: String, params: Map<String, String>?) {
        val bundle = Bundle().apply {
            params?.let {
                for (entry in it.entries) {
                    this.putString(entry.key, entry.value)
                }
            }
        }

        ServiceLocator.provideFirebaseAnalytics(baseContext).logEvent(name, bundle)
    }

    fun setCurrentScreen(activity: Activity, screen: String) {
        ServiceLocator.provideFirebaseAnalytics(activity).setCurrentScreen(activity, screen, null)
    }

    companion object {
        private var selfInstance: Lgr? = null

        @Synchronized
        fun initialize(application: Application) {
            if (selfInstance == null) {
                Starter.start(application)
                selfInstance = Lgr(application.applicationContext)
            } else {
                throw IllegalStateException("Lgr already initialized. Cannot initialize it twice")
            }
        }

        @Synchronized
        fun get(): Lgr =
            selfInstance?.let {
                it
            } ?: throw IllegalStateException("Lgr must be initialized first")
    }
}
