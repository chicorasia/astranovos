package br.com.chicorialabs.astranovos.test

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.domain.GetLatestPostsTitleContainsUseCase
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

/**
 * Essa classe mantém os testes de integração do GetLatestPostsTitleContainsUseCase.
 */
class GetLatestPostsTitleContainsUseCaseTest : KoinTest {

    val getLatestPostsTitleContainsUseCase: GetLatestPostsTitleContainsUseCase by inject()
    val type = "articles"
    val search = "SpaceX"

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
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, search))

            println(result.first().size)

            assertFalse(result.first().isEmpty())
            assertTrue(result is Flow<List<Post>>)
            assertNotNull(result)

        }

    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, search))

            println(result.first().size)
            assertTrue(result is Flow<List<Post>>)
        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, search))

            println(result.first().size)

            assertFalse(result.first().isEmpty())
        }

    }

    /**
     * Cenário de teste:
     * - está recebendo os conteúdos esperados ao realizar a query com busca (integração)
     */
    @Test
    fun deve_RetornarResultadosValidos_AoExecutarABusca() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, search))
            var assertion = true
            result.first().forEach {
                println(it.title)
                assertion = assertion && it.title.contains("SpaceX")
            }
            assertTrue(assertion)
        }
    }
}