package br.com.chicorialabs.astranovos.test

import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.domain.GetLatestPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class GetLatestPostsUseCaseTest : KoinTest {

    val getLatestPostsUseCase: GetLatestPostsUseCase by inject()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setup() {
            configureTestAppComponent()
        }

        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }

    @Test
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio()  {
        runBlocking {
            val result = getLatestPostsUseCase.execute()

            assertNotNull(result)

        }

    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase.execute()
            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio()  {
        runBlocking {
            val result = getLatestPostsUseCase.execute()
            assertFalse(result.first().isEmpty())
        }
    }

}