package br.com.chicorialabs.astranovos.domain

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    /**
     * Essa função encapsula o carregamento dos modules.
     * Não esqueça de invocá-la na classe Application!
     */
    fun load() {
        loadKoinModules(useCaseModule())
    }

    /**
     * Essa função cria os UseCases concretos
     */
    private fun useCaseModule(): Module {
        return module {
            factory { GetLatestPostsUseCase(get()) }
        }
    }

}