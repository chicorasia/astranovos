package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.Resource
import br.com.chicorialabs.astranovos.data.entities.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post, encapsulados em um Resource e emitidos
 * como Flow.
 */
interface PostRepository {

    suspend fun listPosts(category: String) : Flow<Resource<List<Post>>>

    suspend fun listPostsTitleContains(category: String, titleContains: String?) : Flow<Resource<List<Post>>>

    suspend fun toggleIsFavourite(postId: Int) : Boolean

    /**
     * Esse método ainda vai ser útil para o caso de uso de leitura de uma postagem
     */
//    fun getPostWithId(postId: Int) : PostDb
//
//    suspend fun updatePost(postDb: PostDb)

}