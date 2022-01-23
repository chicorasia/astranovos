package br.com.chicorialabs.astranovos.test

import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.domain.GetLatestPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetLatestPostsUseCaseTest : KoinTest{

    val getLatestPostsUseCase: GetLatestPostsUseCase by inject()
    val ARTICLES = "articles"

    companion object {

        @BeforeClass
        @JvmStatic
        fun setup() {
            configureTestAppComponent()
        }

        /**
         * Stop Koin after each test to prevent errors
         */
        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }


    @Test
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio()  {
        runBlocking {
            val result = getLatestPostsUseCase(ARTICLES)

            println(result.first().size)

            assertFalse(result.first().isEmpty())
            assertTrue(result is Flow<List<Post>>)
            assertNotNull(result)

        }

    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(ARTICLES)

            println(result.first().size)
            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio()  {
        runBlocking {
            val result = getLatestPostsUseCase(ARTICLES)

            println(result.first().size)

            assertFalse(result.first().isEmpty())
        }

    }

    @Test
    fun deve_ParsearCorretamente_UmObjetoRecebido() {
        runBlocking {
            val response = getLatestPostsUseCase(ARTICLES)
            val result = response.first().first()

            assertTrue(result is Post)

        }

    }

}