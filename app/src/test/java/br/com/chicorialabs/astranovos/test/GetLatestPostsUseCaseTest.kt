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

/**
 * Essa classe implementa testes para GetLatestPostsUseCase;
 * ela extende a classe KoinTest.
 */
class GetLatestPostsUseCaseTest : KoinTest {

    /**
     * Usar o delegate inject() para instanciar o use case
     */
    val getLatestPostsUseCase: GetLatestPostsUseCase by inject()

    /**
     * Os métodos anotados com @BeforeClass e @AfterClass são executados
     * somente uma vez no início e ao fim do conjunto de testes. Eles
     * precisam ser criados dentro de um companion object para que
     * funcionem como métodos estáticos.
     */
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


    /**
     * Aqui são nossos testes.
     */
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