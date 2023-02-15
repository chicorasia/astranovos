package br.com.chicorialabs.astranovos

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.database.PostDatabase
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Essa classe mantém testes de unidade para PostDatabase. O acesso à database é
 * mediado por um DAO. Os métodos que pretendemos ter são:
 * - dao.listPosts(): retorna resultados no formato de Flow<List<PostDb>
 * - dao.saveAll(dbPosts: List<PostDb>): recebe uma List<PostDb>; não possui retorno
 * - dao.clearDb(): limpa a database; não possui retorno
 * Todos os acessos à db ocorrerão por meio de funções de suspensão.
 */
@RunWith(AndroidJUnit4::class)
class PostDatabaseTest : DbTest() {

    private lateinit var dao: PostDao
    private lateinit var postDatabase: PostDatabase

    /**
     * Instanciando uma PostDatabase em memória antes dos testes
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        postDatabase = Room.inMemoryDatabaseBuilder(
            context,
            PostDatabase::class.java).build()
        dao = postDatabase.dao
    }

    /**
     * Fechar a database depois de executar os testes.
     */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        postDatabase.close()
    }


    /**
     * Testa gravação na database.
     */
    @Test
    fun deve_GravarPostsNaDatabase_AoReceberListaDePosts() {
        //declara um resultado do tipo List<PostDb>
        lateinit var result: List<PostDb>

        runBlocking {
            result = dao.listPosts("articles").first()
        }
        assertTrue(result.isEmpty())
        runBlocking {
            dao.saveAll(articles)
            result = dao.listPosts("articles").first()
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
            dao.saveAll(articles)
            result = dao.listPosts("articles").first()[0]
        }
        assertTrue(result.title == articles[0].title)

    }

    @Test
    fun deve_LimparDatabase_AoInvocarClearDb() {
        lateinit var articlesResult: List<PostDb>
        lateinit var blogsResult: List<PostDb>

        runBlocking {
            dao.saveAll(articles)
            //grava os blog posts na db
            dao.saveAll(blogPosts)
            //apaga postagens da categoria "articles"
            dao.clearDb("articles")
            //recupera buscas na db
            articlesResult = dao.listPosts("articles").first()
            blogsResult = dao.listPosts("blogs").first()
        }
        //testa se a db foi parcialmente apagada de maneira correta
        assertFalse(articlesResult.isEmpty())
        assertFalse(blogsResult.isEmpty())

    }

    @Test
    fun deve_AlternarIsFavourite_AoReceverUmPostId() {

        runBlocking {
            dao.saveAll(blogPosts)
            var post = dao.listPosts("blogs").first()[0]
            println("Post with id ${post.id} is favourite? ${post.isFavourite}")
            assertTrue(!post.isFavourite)
            //recarregue o post da db
            dao.toggleIsFavourite(post.id)
            println("Post with id ${post.id} is favourite? ${post.isFavourite}")
            post = dao.listPosts("blogs").first()[0]
            assertTrue(post.isFavourite)
        }
    //lê um valor da db
        //tentar alterar o isFavourite
        //verifica se foi alterado
    }

    @Test
    fun deve_ApagarApenasPostsNaoFavoritos_AoInvocarClearDb() {

        runBlocking {
            dao.saveAll(articles)
            var result = dao.listPosts("articles").first()
            assertFalse(result.isEmpty())
            dao.clearDb("articles")
            result = dao.listPosts("articles").first()
            assertFalse(result.isEmpty())
        }
    }

    @Test
    fun deve_IgnorarPostPreviamenteGravado_AoAtualizarCache() {

        val postNotFavourite = PostDb(
            id = 12783,
            title = "SpaceX ready for back to back astronaut, Starlink launches",
            url = "https://www.teslarati.com/spacex-back-to-back-starlink-astronaut-launches-crew-3/",
            imageUrl = "https://www.teslarati.com/wp-content/uploads/2021/11/Crew-3-Dragon-C210-F9-B1067-39A-110921-Richard-Angle-feature-2-c.jpg",
            summary = "Two SpaceX Falcon 9 rockets remain on track to attempt back-to-back astronaut and Starlink satellite launches later this week. Both SpaceX East...",
            publishedAt = "2021-11-10T10:07:44.000Z",
            updatedAt = "2021-11-10T10:08:01.340Z",
            launches = emptyArray(),
            category = "articles",
            isFavourite = false
        )

        runBlocking {
            //grava na db
            dao.saveAll(listOf(postNotFavourite))
            val post = dao.listPosts("articles").first().first()
            dao.toggleIsFavourite(post.id) //isFavourite == true
//            dao.clearDb("articles") //limpa todos não-favoritos
            assertTrue(dao.listPosts("articles").first().isNotEmpty())
            dao.saveAll(articles) //salva artigos de teste da classe mãe
            val result = dao.listPosts("articles").first()
            assertTrue(result.size == articles.size) //tem que ter dois itens
            assertTrue(result[0].isFavourite != postNotFavourite.isFavourite)
            val postModified = dao.listPosts("articles").first().first()
            assertTrue(postModified.isFavourite)
        }
    }

}