package br.com.chicorialabs.astranovos.test

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * Esse arquivo mantém a constante BASE_URL, necessária
 * para inicializar os módulos de dados no ambinente de teste.
 * A função configureTestAppComponent() inicia o Koin,
 * carregando os módulos definidos no arquivo
 * ModulesForTest.kt.
 */
const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

fun configureTestAppComponent() = startKoin {
    loadKoinModules(configureDataModuleForTest(baseUrl = BASE_URL) + configureDomainModuleForTest() )
}

