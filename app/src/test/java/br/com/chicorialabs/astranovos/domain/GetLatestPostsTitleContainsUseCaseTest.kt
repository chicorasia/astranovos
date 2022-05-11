package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.test.configureTestAppComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class GetLatestPostsTitleContainsUseCaseTest : KoinTest {

    val getLatestPostsTitleContainsUseCase: GetLatestPostsTitleContainsUseCase by inject()
    private val type = SpaceFlightNewsCategory.ARTICLES.value
    private val searchString = "mars"

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
    fun deve_RetornarResultadosValidos_AoExecutarBusca() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, searchString))
            var assertion = true
            result.first().forEach{ post ->
                println(post.title)
                assertion = assertion && post.title.lowercase().contains(searchString)
            }
            assertTrue(assertion)
        }

    }

}