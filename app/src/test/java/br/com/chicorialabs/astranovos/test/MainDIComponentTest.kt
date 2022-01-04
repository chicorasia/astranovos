package br.com.chicorialabs.astranovos.test

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

fun configureTestAppComponent() = startKoin {
    loadKoinModules(configureNetworkModuleForTest(BASE_URL) + configureUseCaseForTest())
}