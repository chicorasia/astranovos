package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.Resource
import br.com.chicorialabs.astranovos.data.entities.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post.
 */
interface PostRepository {

    suspend fun listPosts(category: String) : Flow<Resource<List<Post>>>

    suspend fun listPostsTitleContains(
        category: String,
        titleContains: String?) : Flow<Resource<List<Post>>>
}