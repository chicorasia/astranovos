package br.com.chicorialabs.astranovos.domain.di

import br.com.chicorialabs.astranovos.domain.GetLatestPostsTitleContainsUseCase
import br.com.chicorialabs.astranovos.domain.GetLatestPostsUseCase
import br.com.chicorialabs.astranovos.domain.GetSinglePostByIdUseCase
import br.com.chicorialabs.astranovos.domain.ToggleIsFavouriteUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Esse objeto dá apoio à instanciação dos use cases com suas dependências.
 * Segue o mesmo padrão adotado nos outros pacotes.
 */
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
            factory { GetLatestPostsUseCase(repository = get()) }
            factory { GetLatestPostsTitleContainsUseCase(repository = get()) }
            factory { ToggleIsFavouriteUseCase(repository = get()) }

            //adicionar a dependência do novo use case
            factory { GetSinglePostByIdUseCase(repository = get()) }
        }
    }

}