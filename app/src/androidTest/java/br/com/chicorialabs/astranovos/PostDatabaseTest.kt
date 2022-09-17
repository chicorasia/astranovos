package br.com.chicorialabs.astranovos

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.database.PostDatabase
import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
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
import kotlin.jvm.Throws

//Parte D
//TODO 020: Instanciar a database em memória para os testes ✅
//TODO 021: [Refatoração] Criar uma superclasse DbTest para organizar as entidades ✅
//TODO 022: [Refatoração] Modificar a assinatura de PostDatabaseTest para extender DbTest ✅

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
        lateinit var result: List<PostDb>
        runBlocking {
            dao.saveAll(dbPosts)
            dao.clearDb()
            result = dao.listPosts().first()
        }
        assertTrue(result.isEmpty())
    }


}