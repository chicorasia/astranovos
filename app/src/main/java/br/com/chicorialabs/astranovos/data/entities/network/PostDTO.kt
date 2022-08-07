package br.com.chicorialabs.astranovos.data.entities.network

import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.model.Post

/**
 * Essa classe representa um objeto recebido da API. Ela segue a estrutura
 * inicial da classe Post e possui um método de conveniência para conversão em
 * entidade de modelo.
 * TODO: modificar o método de conveniência para converter em entidade de db
 */
data class PostDTO(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: Array<LaunchDTO> = emptyArray()
) {

    /**
     * Um método de conveniência para converter em entidade de modelo.
     */
    fun toModel() : Post =
        Post(
            id = id,
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            publishedAt = publishedAt,
            updatedAt = updatedAt,
            launches = launches.toModel()
        )

    fun toDb() : PostDb =
        PostDb(
            id = id,
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            publishedAt = publishedAt,
            updatedAt = updatedAt,
            launches = launches.toDb()
        )
}
fun List<PostDTO>.toModel() : List<Post> =
    this.map {
        it.toModel()
    }

fun List<PostDTO>.toDb() : List<PostDb> =
    this.map {
        it.toDb()
    }


