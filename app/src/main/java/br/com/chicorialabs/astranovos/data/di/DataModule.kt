package br.com.chicorialabs.astranovos.data.di

import br.com.chicorialabs.astranovos.data.repository.MockAPIService
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * Esse object é responsável por instanciar e configurar os serviços web. Está com duas
 * responsabilidades (epa!): configurar os serviços web (OkHttp e Retrofit) e fazer
 * a injeção de dependências por meio do Koin.
 */
object DataModule {

    // TODO 014: Adicionar uma constante BASE_URL
    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

    //TODO 007: Criar uma função pública load()
    fun load() {
        loadKoinModules(postsModule() + networkModule())
    }

    //TODO 006: Criar uma função privada postsModule() para instanciar o PostRepository
    private fun postsModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(get()) }
        }
    }

    /**
     * Cria um módulo de rede
     */
    private fun networkModule() : Module {
        return module {

            /**
             * Instancia uma factory do Moshi
             */
            single {
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            }

            /**
             * Cria o serviço de rede usando o Retrofit
             */
            single {
                createService(get())
            }

        }
    }
    /**
     * Essa função usa o Retrofit para criar um SpaceFlightNewsService
     */
    private inline fun createService(
        factory: Moshi,
    ) : SpaceFlightNewsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)
    }

}