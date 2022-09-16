package br.com.chicorialabs.astranovos

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Parte D
//TODO 020: Instanciar a database em memória para os testes
//TODO 021: [Refatoração] Criar uma superclasse DbTest para organizar as entidades
//TODO 022: [Refatoração] Modificar a assinatura de PostDatabaseTest para extender DbTest

/**
 * Essa classe mantém testes de unidade para PostDatabase. O acesso à database é
 * mediado por um DAO. Os métodos que pretendemos ter são:
 * - dao.listPosts(): retorna resultados no formato de Flow<List<PostDb>
 * - dao.saveAll(dbPosts: List<PostDb>): recebe uma List<PostDb>; não possui retorno
 * - dao.clearDb(): limpa a database; não possui retorno
 * Todos os acessos à db ocorrerão por meio de funções de suspensão.
 */
@RunWith(AndroidJUnit4::class)
class PostDatabaseTest {

    //uma lista de objetos PostDb para testes de unidade
    private lateinit var dbPosts: List<PostDb>

    //esse método cria objetos do tipo PostDb para povoar a lista
    @Before
    fun createPostsForTest() {
        //um Post sem eventos de lançamento
        val postNoLaunches = PostDb(
            id = 12783,
            title = "SpaceX ready for back to back astronaut, Starlink launches",
            url = "https://www.teslarati.com/spacex-back-to-back-starlink-astronaut-launches-crew-3/",
            imageUrl = "https://www.teslarati.com/wp-content/uploads/2021/11/Crew-3-Dragon-C210-F9-B1067-39A-110921-Richard-Angle-feature-2-c.jpg",
            summary = "Two SpaceX Falcon 9 rockets remain on track to attempt back-to-back astronaut and Starlink satellite launches later this week. Both SpaceX East...",
            publishedAt = "2021-11-10T10:07:44.000Z",
            updatedAt = "2021-11-10T10:08:01.340Z",
            launches = emptyArray(),
        )

        //um post com eventos de lançamento
        val postWithLaunches = PostDb(
            id = 12782,
            title = "Crew-3 mission cleared for launch",
            url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
            summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
            publishedAt = "2021-11-10T09:27:02.000Z",
            updatedAt = "2021-11-10T09:38:23.654Z",
            launches = arrayOf(
                LaunchDb(
                    id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
                    provider = "Launch Library 2"
                )
            )

        )

        //cria uma lista com os dois objetos e atribui a dbPosts
        dbPosts = listOf(postWithLaunches, postNoLaunches)

    }

    /**
     * Testa gravação na database.
     */
    @Test
    fun deve_GravarPostsNaDatabase_AoReceberListaDePosts() {
        //declara um resultado do tipo List<PostDb>
        lateinit var result: List<PostDb>

        runBlocking {
            result = dao.listPosts().first()
        }
        assertTrue(result.isEmpty())
        runBlocking {
            dao.saveAll(dbPosts)
            result = dao.listPosts().first()
        }
        assertFalse(result.isEmpty())
    }

    /**
     * Testa a busca de postagens na database.
     */
    @Test
    fun deve_RetornarPostsCorretamente_AoLerDaDatabase() {
       lateinit var result: PostDb
        runBlocking {
            dao.saveAll(dbPosts)
            result = dao.listPosts().first()[0]
        }
        assertTrue(result.title == dbPosts[0].title)

    }

    @Test
    fun deve_LimparDatabase_AoInvocarClearDb() {
        runBlocking {
            dao.saveAll(dbPosts)
            dao.clearDb()
        }
        assertTrue(dao.listPosts().isEmpty())
    }

    //Um teste bobo só pra mostrar que ele roda no dispositivo ou no emulador
    @Test
    fun doisMaisDois_naoEhCinco() {
        assertTrue(2 + 2 != 5)
    }

}