package br.com.chicorialabs.astranovos.test

import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import br.com.chicorialabs.astranovos.domain.GetLatestPostsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Esse método cria os módulos da camada de dados para teste
 */
fun configureDataModuleForTest(baseUrl: String) = module {
    single<SpaceFlightNewsService> {

        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        createServiceForTest(factory = factory, baseUrl = baseUrl)
    }

    single<PostRepository> { PostRepositoryImpl(get()) }

}

/**
 * Essa função configura os módulos da camada de domíno para teste
 */

//TODO 006a: Adicionar GetLatestPostTitleContainsUseCase à lista de domínios
fun configureDomainModuleForTest() = module {
    factory<GetLatestPostsUseCase> { GetLatestPostsUseCase(get()) }
}

/**
 * Essa é uma versão simplificada do método createService()
 * presente no DataModule.
 * @param factory Fábrica de conversor/parseador
 * @param baseUrl Endereço base da API no formato "http://..."
 */
private inline fun <reified T> createServiceForTest(
    factory: Moshi,
    baseUrl: String
) : T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(factory))
        .build()
        .create(T::class.java)
}
