package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.data.entities.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post.
 */
interface PostRepository {

//    TODO 007: Modificar o retorno do método listPosts() para devolver um Flow<Resource<List<Post>>
    suspend fun listPosts(category: String) : Flow<List<Post>>

//    TODO 018: Modificar o retorno do método listPostsTitleContains() para devolver um Flow<Resource<List<Post>>
    suspend fun listPostsTitleContains(category: String, titleContains: String?) : Flow<List<Post>>
}