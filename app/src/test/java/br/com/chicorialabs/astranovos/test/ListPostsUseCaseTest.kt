package br.com.chicorialabs.astranovos.test

import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.domain.ListPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ListPostsUseCaseTest : KoinTest{

    val listPostsUseCase: ListPostsUseCase by inject()


    @Before
    fun setup() {
        configureTestAppComponent()
    }

    /**
     * Stop Koin after each test to prevent errors
     */
    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio()  {
        runBlocking {
            val result = listPostsUseCase()

            println(result.first().size)

            assertFalse(result.first().isEmpty())
            assertTrue(result is Flow<List<Post>>)
            assertNotNull(result)

        }

    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio() {
        runBlocking {
            val result = listPostsUseCase()

            println(result.first().size)
            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio()  {
        runBlocking {
            val result = listPostsUseCase()

            println(result.first().size)

            assertFalse(result.first().isEmpty())
        }

    }

    @Test
    fun deve_ParsearCorretamente_UmObjetoRecebido() {
        runBlocking {
            val response = listPostsUseCase()
            val result = response.first().first()

            assertTrue(result is Post)

        }

    }

}