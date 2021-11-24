package br.com.chicorialabs.astranovos.data.di

import android.util.Log
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Esse object é reponsável pelas dependências da camada data.
 * Segue o mesmo padrão de injeção de dependência por camadas
 * adotado no PresentationModule, porém com mais componentes.
 */


// Tarefas do SpaceFlightNewsService


// Tarefas do DataModule

object DataModule {

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
    private const val OK_HTTP = "Ok http"
    /**
     * Carrega os módulos dessa camada. Essa função é
     * invocada pela classe App quando o app é inicializado.
     */
    fun load() {
        loadKoinModules(postsModule() + networkModule())
    }

    /**
     * Cria uma instância de PostRepositoryImpl usando o service
     * mockado.
     */
    private fun postsModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(get()) }
        }
    }

    private fun networkModule() : Module {
        return module {
            single<SpaceFlightNewsService> { createService(get(), get()) }

            single {
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            }

            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it )
                }

                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
        }
    }

    private inline fun <reified T> createService(
        factory: Moshi,
        client: OkHttpClient
    ) : T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
            .create(T::class.java)
    }

}