package es.mnmapp.aolv.meneame.di

import org.koin.dsl.module.Module

/**
 * Created by antonio on 3/18/18.
 */

fun getKoinModules(): List<Module> = listOf(
        appModule,
        repositoryModule,
        mainActivityModule,
        testingModule
)