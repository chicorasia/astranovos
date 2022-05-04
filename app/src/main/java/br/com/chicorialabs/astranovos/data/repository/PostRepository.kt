package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.data.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post.
 */
interface PostRepository {

    suspend fun listPosts(category: String) : Flow<List<Post>>

    suspend fun listPostsTitleContains(category: String, titleContains: String?) : Flow<List<Post>>
}