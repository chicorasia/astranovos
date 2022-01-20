package br.com.chicorialabs.astranovos

import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
open class SpaceFlightNewsServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var service: SpaceFlightNewsService

    @Before
    fun createService() {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun deve_ReceberOsPosts_AoConectarComOService() {
        runBlocking {
            mockWebServer.enqueueFromFile("src/test/java/br/com/chicorialabs/astranovos/latest_articles.json")
            val response = service.listPosts()
            println(response.count())
            assert(response.count() == 10)

        }
    }



}