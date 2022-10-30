package br.com.chicorialabs.astranovos.data.entities.network

import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.model.Post

/**
 * Essa data class representa um objeto recebido da API e serve como
 * entidade intermediária entre a API e o app, reduzindo o
 * acoplamento e resolvendo a dependência de uma camada externa ao app.
 * Ela possui métodos de conveniência para conversão em entidade de modelo
 * - como essa conversão unidirecional, não precisamos converter de modelo para DTO.
 * Ela é complementada por LaunchDTO.
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

    fun toModel() : Post = Post(
            id = id,
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            publishedAt = publishedAt,
            updatedAt = updatedAt,
            launches = launches.toModel()
        )

        fun toDb() : PostDb = PostDb(
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

/**
 * Um método de conveniência para converter uma lista inteira de PostDTO.
 */
fun List<PostDTO>.toModel() : List<Post> =
    this.map {
        it.toModel()
    }

fun List<PostDTO>.toDb() : List<PostDb> =
    this.map {
        it.toDb()
    }
