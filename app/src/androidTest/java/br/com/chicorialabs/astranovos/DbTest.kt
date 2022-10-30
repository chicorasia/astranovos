package br.com.chicorialabs.astranovos

import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import org.junit.Before

/**
 * Essa classe aberta ajuda a manter organizados os testes
 * de unidade da PostDatabase.
 */
open class DbTest {

    //uma lista de objetos PostDb para testes de unidade
    lateinit var dbPosts: List<PostDb>

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

}
