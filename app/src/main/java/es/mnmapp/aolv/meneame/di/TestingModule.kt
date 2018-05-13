package es.mnmapp.aolv.meneame.di

import es.mnmapp.aolv.data.testing.SimpleIdlingResource
import org.koin.dsl.module.applicationContext

val testingModule = applicationContext {
    bean { SimpleIdlingResource() }
}