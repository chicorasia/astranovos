package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.core.Resource
import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

/**
 * Esse use case recebe uma Query com uma string de busca
 * e retorna todas as postagens do tipo ativo que contenham
 * a string de busca no título.
 */
class GetLatestPostsTitleContainsUseCase(private val repository: PostRepository) :
    UseCase<Query, Resource<List<Post>>>() {

    override suspend fun execute(param: Query): Flow<Resource<List<Post>>> =
        repository.listPostsTitleContains(param.type, param.option)

}