package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToggleIsFavouriteUseCase(private val repository: PostRepository) : UseCase<Int, Post>() {

    override suspend fun execute(param: Int): Flow<Nothing> = flow {
        repository.toggleIsFavourite(param)
    }

}