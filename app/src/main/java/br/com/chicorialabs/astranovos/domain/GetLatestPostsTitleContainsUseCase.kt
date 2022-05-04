package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsTitleContainsUseCase(private val repository: PostRepository) :
    UseCase<Query, List<Post>>() {

    override suspend fun execute(param: Query): Flow<List<Post>> =
        repository.listPostsTitleContains(param.type, param.option)

}