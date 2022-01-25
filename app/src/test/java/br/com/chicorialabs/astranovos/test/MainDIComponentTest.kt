package br.com.chicorialabs.astranovos.test

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * Essa constante mantém a URL base da api e é usada pelo Koin
 * para instanciar serviços.
 */
const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

/**
 * Essa função inicializa o Koin e seus módulos somente
 * para os testes.
 *
 */
fun configureTestAppComponent() = startKoin {
    loadKoinModules(configureDataModuleForTest(BASE_URL) + configureDomainModuleForTest())
}
