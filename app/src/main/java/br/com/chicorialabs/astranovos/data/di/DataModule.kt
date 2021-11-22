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

// TODO 003: Criar um pacote data.services
// TODO 004: Criar uma interface SpaceFlightNewsService no pacote data.services

// Tarefas do SpaceFlightNewsService
// TODO 005: Adicionar o endpoint 'articles' ao SpaceFlightNewsService

// Tarefas do DataModule
// TODO 009: Criar uma constante BASE_URL
// TODO 010: Criar uma função createService() que retorna um SpaceFlightNewsService
// TODO 011: Criar uma função privada networkModule()
// TODO 012: Usar a função createService para obter o SpaceFlightNewsService
// TODO 013: Adicionar o networkModule() à função load()
// TODO 014: Adicionar uma converter factory
// TODO 015: Adicionar uma constante OK_HTTP
// TODO 016: Criar um logging interceptor usando o OkHttp3
// TODO 017: Adicionar o parâmetro OkHttpClient ao createService()
// TODO 018: Refatorar a função createService para usar generics

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