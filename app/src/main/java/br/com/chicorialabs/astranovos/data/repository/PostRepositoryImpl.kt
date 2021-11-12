package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.data.model.Launch
import br.com.chicorialabs.astranovos.data.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


/**
 * Essa classe implementa a interface PostRepository, inicialmente
 * usando um serviço mockado. Os dados são retornados na forma de um flow.
 */

class PostRepositoryImpl(private val service: MockAPIService) : PostRepository {

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados. Aqui a função acessa um serviço
     * mockado. No uso real é preciso usar um bloco try-catch para
     * lidar com exceções no acesso à API.
     */
    override suspend fun listPosts(): Flow<List<Post>> = flow {

        val postList = service.listPosts
        emit(postList)

    }
}

/**
 * Um serviço com dados mockados só para apoio durante o desenvolvimento.
 */

object MockAPIService {


    val listPosts: List<Post> = listOf(
        Post(
            id = 12783,
            title = "SpaceX ready for back to back astronaut, Starlink launches",
            url = "https://www.teslarati.com/spacex-back-to-back-starlink-astronaut-launches-crew-3/",
            imageUrl = "https://www.teslarati.com/wp-content/uploads/2021/11/Crew-3-Dragon-C210-F9-B1067-39A-110921-Richard-Angle-feature-2-c.jpg",
            summary = "Two SpaceX Falcon 9 rockets remain on track to attempt back-to-back astronaut and Starlink satellite launches later this week. Both SpaceX East...",
            publishedAt = "2021-11-10T10:07:44.000Z",
            updatedAt = "2021-11-10T10:08:01.340Z",
        ),
        Post(
            id = 12782,
            title = "Crew-3 mission cleared for launch",
            url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
            summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
            publishedAt = "2021-11-10T09:27:02.000Z",
            updatedAt = "2021-11-10T09:38:23.654Z",
            launches = arrayOf(
                Launch(
                    id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
                    provider = "Launch Library 2"
                ),
                Launch(
                    id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
                    provider = "Launch Library 2"
                )
            )
        ),
        Post(
            id = 12781,
            title = "NASA delays Moon landings, says Blue Origin legal tactics partly to blame",
            url = "https://arstechnica.com/science/2021/11/nasa-delays-moon-landings-says-blue-origin-legal-tactics-partly-to-blame/",
            imageUrl = "https://cdn.arstechnica.net/wp-content/uploads/2021/10/51614473753_88c81a224f_k.jpg",
            summary = "\"We've lost nearly seven months in litigation.\"",
            publishedAt = "2021-11-10T00:34:02.000Z",
            updatedAt = "2021-11-10T05:47:30.161Z",
            launches = arrayOf(
                Launch(
                    id = "8034d81b-af96-460c-a7b7-5c8e7f1a1d86",
                    provider = "Launch Library 2"
                )
            )
        ),
        Post(
            id = 12776,
            title = "U.S. interagency panel to update R&D strategy to tackle orbital debris",
            url = "https://spacenews.com/u-s-interagency-panel-to-update-rd-strategy-to-tackle-orbital-debris/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2016/05/Orbital_Debris_ESA-879x485.jpg",
            summary = "The Biden administration plans to update an existing research-and-development plan aimed at combatting orbital debris.",
            publishedAt = "2021-11-09T21:57:00.000Z",
            updatedAt = "2021-11-09T21:57:00.333Z",
        ),
        Post(
            id = 12777,
            title = "Spaceflight to launch 13 payloads on first multi-orbit space tug mission",
            url = "https://spacenews.com/spaceflight-to-launch-13-payloads-on-first-multi-orbit-space-tug-mission/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/LTC-scaled.jpg",
            summary = "Launch services provider Spaceflight announced plans Nov. 9 to deliver 13 customer payloads across two distinct orbits for the first time next year with its new space tug.",
            publishedAt = "2021-11-09T21:57:00.000Z",
            updatedAt = "2021-11-09T21:57:00.335Z",
        )
    )

}