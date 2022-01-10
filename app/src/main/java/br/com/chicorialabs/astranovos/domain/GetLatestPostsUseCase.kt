package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

/**
 * Essa classe extende um UseCase abstrato, recebendo um objeto do tipo
 * PostRepository como dependência. Sua finalidade é implementar
 * a função de buscar os últimos posts no repositório.
 */
//TODO 005: Modificar  GetLatestPostsUseCase para implementar NoParam<List<Post>>

class GetLatestPostsUseCase(private val repository: PostRepository) : UseCase.NoParam<List<Post>>() {

    override suspend fun execute(): Flow<List<Post>> = repository.listPosts()

}