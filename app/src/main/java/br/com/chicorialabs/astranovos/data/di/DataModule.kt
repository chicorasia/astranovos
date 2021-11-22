package br.com.chicorialabs.astranovos.data.di

import br.com.chicorialabs.astranovos.data.repository.MockAPIService
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Esse object é reponsável pelas dependências da camada data.
 * Segue o mesmo padrão de injeção de dependência por camadas
 * adotado no PresentationModule, porém com mais componentes.
 */
object DataModule {

    /**
     * Carrega os módulos dessa camada. Essa função é
     * invocada pela classe App quando o app é inicializado.
     */
    fun load() {
        loadKoinModules(postsModule())
    }

    /**
     * Cria uma instância de PostRepositoryImpl usando o service
     * mockado.
     */
    private fun postsModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(MockAPIService) }
        }
    }

}