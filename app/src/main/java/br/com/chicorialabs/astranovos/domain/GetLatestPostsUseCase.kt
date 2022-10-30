package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

/**
 * Esse use case retorna todas as postagens do tipo ativo
 */
//TODO 006: Modificar GetLatestPostsUseCase para retornar um Flow<Resource<List<Post>>>
class GetLatestPostsUseCase(private val repository: PostRepository) : UseCase<Query, List<Post>>() {

    override suspend fun execute(param: Query): Flow<List<Post>> =
        repository.listPosts(param.type)

}