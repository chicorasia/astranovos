package br.com.chicorialabs.astranovos.data.network

import br.com.chicorialabs.astranovos.data.model.Post
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PostDTOTest {

    private val postDto = PostDTO(
        id = 12783,
        title = "SpaceX ready for back to back astronaut, Starlink launches",
        url = "https://www.teslarati.com/spacex-back-to-back-starlink-astronaut-launches-crew-3/",
        imageUrl = "https://www.teslarati.com/wp-content/uploads/2021/11/Crew-3-Dragon-C210-F9-B1067-39A-110921-Richard-Angle-feature-2-c.jpg",
        summary = "Two SpaceX Falcon 9 rockets remain on track to attempt back-to-back astronaut and Starlink satellite launches later this week. Both SpaceX East...",
        publishedAt = "2021-11-10T10:07:44.000Z",
        updatedAt = "2021-11-10T10:08:01.340Z",
        launches = emptyArray<LaunchDTO>()
    )

    @Test
    fun `deve converter corretamente em entidade de modelo`() {
        val post = postDto.toModel()
        assertTrue(post is Post)
        assertTrue(post.title == postDto.title)
        assertTrue(post.launches.isEmpty())
    }
}