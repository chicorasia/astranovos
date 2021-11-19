package br.com.chicorialabs.astranovos.data.di

import br.com.chicorialabs.astranovos.data.repository.MockAPIService
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Esse object é responsável por instanciar e configurar os serviços web. Está com duas
 * responsabilidades (epa!): configurar os serviços web (OkHttp e Retrofit) e fazer
 * a injeção de dependências por meio do Koin.
 */
object DataModule {

    //TODO 007: Criar uma função pública load()
    fun load() {
        loadKoinModules(postsModule())
    }


    //TODO 006: Criar uma função privada postsModule() para instanciar o PostRepository
    private fun postsModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(MockAPIService) }
        }
    }

}