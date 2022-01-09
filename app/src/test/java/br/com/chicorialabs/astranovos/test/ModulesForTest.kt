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

fun configureDomainModuleForTest() = module {
    factory<GetLatestPostsUseCase> { GetLatestPostsUseCase(get()) }
}

fun configureDataModuleForTest(baseUrl: String) = module {

    single {

        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)
    }

    single<PostRepository> { PostRepositoryImpl(get()) }

}