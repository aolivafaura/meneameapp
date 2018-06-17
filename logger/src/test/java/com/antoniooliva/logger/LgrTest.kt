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

package com.antoniooliva.logger

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import timber.log.Timber

/**
 * Test for Lgr class
 * Initializer stuff makes necessary to run those test in a specific order
 *
 * @see Lgr
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LgrTest {

    // Fields -----

    private val debugTree: Timber.DebugTree = mock()
    private val analytics: FirebaseAnalytics = mock()
    private val message: String = "Test message"

    @Before
    fun setUp() {
        mockkObject(ServiceLocator, Starter)
        every { ServiceLocator.provideTimber(any()) } returns debugTree
        every { ServiceLocator.provideAnalyticsLogger(any()) } returns analytics
        every { Starter.start(any()) } returns Unit
    }

    @After
    fun after() {
        unmockkAll()
    }

    // Test cases -----

    @Test
    fun `tA, Given logger not initialized, when instance is get, then exception is thrown`() {
        shouldThrowException { Lgr.get() }
    }

    @Test
    fun `tB, Given logger initialized, when is initialized for second time, then exception is thrown`() {
        initializeLgr()
        shouldThrowException { Lgr.initialize(mock()) }
    }

    @Test
    fun `tB, Given logger initialized, when debug message is log, then provided logger is used to log it`() {
        // Given
        initializeLgr()
        // When
        Lgr.get().d(message)
        // Then
        verify(debugTree, times(1)).d(message)
    }

    @Test
    fun `tB, Given logger initialized, when warn message is log, then provided logger is used to log it`() {
        // Given
        initializeLgr()
        // When
        Lgr.get().w(message)
        // Then
        verify(debugTree, times(1)).w(message)
    }

    @Test
    fun `tB, Given logger initialized, when info message is log, then provided logger is used to log it`() {
        // Given
        initializeLgr()
        // When
        Lgr.get().i(message)
        // Then
        verify(debugTree, times(1)).i(message)
    }

    @Test
    fun `tB, Given logger initialized, when verbose message is log, then provided logger is used to log it`() {
        // Given
        initializeLgr()
        // When
        Lgr.get().v(message)
        // Then
        verify(debugTree, times(1)).v(message)
    }

    @Test
    fun `tB, Given logger initialized, when error message is log, then provided logger is used to log it`() {
        // Given
        initializeLgr()
        // When
        Lgr.get().e(message)
        // Then
        verify(debugTree, times(1)).e(message)
    }

    // Private methods -----

    private fun initializeLgr() {
        val application: Application = mock()
        whenever(application.applicationContext).thenReturn(mock())
        if (!Lgr.isInitialized()) {
            Lgr.initialize(application)
        }
    }
}
