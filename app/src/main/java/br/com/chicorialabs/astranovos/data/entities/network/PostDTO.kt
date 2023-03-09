package br.com.chicorialabs.astranovos.data.entities.network

import br.com.chicorialabs.astranovos.data.entities.db.PostDb

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

        fun toDb(category: String) : PostDb = PostDb(
            id = id,
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            publishedAt = publishedAt,
            updatedAt = updatedAt,
            launches = launches.toDb(),
            category = category,
        )
}

/**
 * Um método de conveniência para converter uma lista inteira de PostDTO.
 */

fun List<PostDTO>.toDb(category: String) : List<PostDb> =
    this.map {
        it.toDb(category)
    }
