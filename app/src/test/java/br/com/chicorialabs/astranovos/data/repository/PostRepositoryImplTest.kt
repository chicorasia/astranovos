package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.SpaceFlightNewsServiceTest
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.enqueueFromFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class PostRepositoryImplTest : SpaceFlightNewsServiceTest() {

    lateinit var postRepository: PostRepository

    @Before
    fun setup() {
        postRepository = PostRepositoryImpl(service)
    }


    @Test
    fun deve_EmitirResultadoComoFlow_AoConectarComServico() {

        runBlocking {
            mockWebServer.enqueueFromFile("src/test/java/br/com/chicorialabs/astranovos/latest_articles.json")
            val response = postRepository.listPosts()
            assertTrue(response is Flow<List<Post>>)
        }

    }

    @Test(expected = RemoteException::class)
    fun deve_LançarException_AoOcorrerErroDeConexao() {
        runBlocking {
            val mockResponse = MockResponse().apply {
                setResponseCode(500)
            }
            mockWebServer.enqueue(mockResponse)
            println(postRepository.listPosts().first().first())
        }

    }

    @Test
    fun deve_ParsearCorretamente_UmObjetoJSON() {
        runBlocking {
            mockWebServer.enqueueFromFile("src/test/java/br/com/chicorialabs/astranovos/latest_articles.json")
            val response = postRepository.listPosts()

            val result: Post = response.first()[0]

            assertTrue(result.title == "Arianespace looks to transitions of vehicles and business in 2022")
            assertTrue(result.url == "https://spacenews.com/arianespace-looks-to-transitions-of-vehicles-and-business-in-2022/")

        }
    }



}