package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.meneame.testing.SimpleIdlingResource
import org.koin.dsl.module.applicationContext

val testingModule = applicationContext {
    bean { SimpleIdlingResource() }
}