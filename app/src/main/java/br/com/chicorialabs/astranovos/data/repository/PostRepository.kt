package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.data.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um repositório para
 * os objetos do tipo Post.
 */
//TODO 009: Atualizar o método listPosts() da interface PostRepository
interface PostRepository {

    suspend fun listPosts() : Flow<List<Post>>
}