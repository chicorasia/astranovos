package br.com.chicorialabs.astranovos

import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.network.LaunchDTO
import br.com.chicorialabs.astranovos.data.network.PostDTO
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Essa classe mantém testes de unidade para a entidades PostDTO e LaunchDTO;
 * O que queremos testar aqui é a conversão de DTO em entidades de modelo.
 * Esse é um teste mínimo - fique à vontade para adicionar mais testes.
 *
 */
@RunWith(JUnit4::class)
class PostDTOTest {

    //declarar um objeto do tipo LaunchDTO
    private val launchDto = LaunchDTO(
        id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
        provider = "Launch Library 2"
    )

    //declarar um objeto do tipo PostDTO
    private val postDto = PostDTO(
        id = 12783,
        title = "SpaceX ready for back to back astronaut, Starlink launches",
        url = "https://www.teslarati.com/spacex-back-to-back-starlink-astronaut-launches-crew-3/",
        imageUrl = "https://www.teslarati.com/wp-content/uploads/2021/11/Crew-3-Dragon-C210-F9-B1067-39A-110921-Richard-Angle-feature-2-c.jpg",
        summary = "Two SpaceX Falcon 9 rockets remain on track to attempt back-to-back astronaut and Starlink satellite launches later this week. Both SpaceX East...",
        publishedAt = "2021-11-10T10:07:44.000Z",
        updatedAt = "2021-11-10T10:08:01.340Z",
        launches = arrayOf(launchDto)
    )

    @Test
    fun `deve converter corretamente em entidade de modelo`() {
        val post: Post = postDto.toModel()

        //testar se o objeto convertido é do tipo certo
        assertTrue(post is Post)
        //... se o atributo title do objeto DTO está certo...
        assertTrue(post.title == postDto.title)
        //... e se o atributo launches não está vazio.
        assertTrue(post.launches.isNotEmpty())
    }

}