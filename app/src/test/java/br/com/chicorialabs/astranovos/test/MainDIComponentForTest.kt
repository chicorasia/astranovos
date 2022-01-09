package br.com.chicorialabs.astranovos.test

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

fun configureTestAppComponent() = startKoin {
    loadKoinModules(configureDataModuleForTest(baseUrl = BASE_URL) + configureDomainModuleForTest() )
}

