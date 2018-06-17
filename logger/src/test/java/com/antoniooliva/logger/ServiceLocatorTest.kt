package com.antoniooliva.logger

import com.antoniooliva.logger.tree.CrashlyticsTree
import org.junit.Assert.assertTrue
import org.junit.Test
import timber.log.Timber

class ServiceLocatorTest {

    @Test
    fun `Given timber object request, when compilation is debug, then debug tree is returned`() {
        assertTrue(ServiceLocator.provideTimber(true) is Timber.DebugTree)
    }

    @Test
    fun `Given timber object request, when compilation is not debug, then crashlytics tree is returned`() {
        assertTrue(ServiceLocator.provideTimber(false) is CrashlyticsTree)
    }
}