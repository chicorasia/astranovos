package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSinglePostByIdUseCase(private val repository: PostRepository) : UseCase<Int, PostDb>() {

    override suspend fun execute(param: Int): Flow<PostDb> = flow {
        emit(repository.getPostById(param))
    }
}
