package br.com.chicorialabs.astranovos

import android.content.Context
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
            PostDatabase::class.java
        ).build()
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
//    TODO 005: Modificar o teste de gravação na db
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
//    TODO 006: Modificar o teste de leitura da Db
    @Test
    fun deve_RetornarPostsCorretamente_AoLerDaDatabase() {
        lateinit var result: PostDb
        runBlocking {
            dao.saveAll(articles)
            result = dao.listPosts("articles").first()[0]
        }
        assertTrue(result.title == articles[0].title)

    }

    //    TODO 007: Modificar o teste de limpeza da Db
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
        assertTrue(articlesResult.isEmpty())
        assertFalse(blogsResult.isEmpty())
    }


}