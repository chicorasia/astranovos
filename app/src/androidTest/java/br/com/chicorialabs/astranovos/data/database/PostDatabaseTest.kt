package br.com.chicorialabs.astranovos.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostDatabaseTest : DbTest() {

    private lateinit var dao: PostDao
    private lateinit var postDatabase: PostDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        postDatabase = Room.inMemoryDatabaseBuilder(
            context,
            PostDatabase::class.java
        ).build()
        dao = postDatabase.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        postDatabase.close()
    }

    //testar se a Database é criada vazia
    @Test
    fun deve_InicializarDatabaseVazia() {
        val result = dao.listPosts()
        assertTrue(result.isEmpty())
    }


    @Test
    fun deve_GravarPostsNaDatabase_AoReceberListaDePosts() {
        assertTrue(dao.listPosts().isEmpty())
        runBlocking {
            dao.saveAll(dbPosts)
        }
        assertFalse(dao.listPosts().isEmpty())
    }

    //testar se o método clearDb() limpa a database

    @Test
    fun deve_RetornarPostsCorretamente_AoLerDaDatabase() {
        assertTrue(dao.listPosts().isEmpty())
        //grava os posts na database
        runBlocking {
            dao.saveAll(dbPosts)
        }
        val result = dao.listPosts()[0]
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

}

open class DbTest {

    lateinit var dbPosts: List<PostDb>

    @Before
    fun createPostsForTest() {

        val postNoLaunches = PostDb(
            id = 12783,
            title = "SpaceX ready for back to back astronaut, Starlink launches",
            url = "https://www.teslarati.com/spacex-back-to-back-starlink-astronaut-launches-crew-3/",
            imageUrl = "https://www.teslarati.com/wp-content/uploads/2021/11/Crew-3-Dragon-C210-F9-B1067-39A-110921-Richard-Angle-feature-2-c.jpg",
            summary = "Two SpaceX Falcon 9 rockets remain on track to attempt back-to-back astronaut and Starlink satellite launches later this week. Both SpaceX East...",
            publishedAt = "2021-11-10T10:07:44.000Z",
            updatedAt = "2021-11-10T10:08:01.340Z",
        )

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

        dbPosts = listOf(postWithLaunches, postNoLaunches)

    }

}
