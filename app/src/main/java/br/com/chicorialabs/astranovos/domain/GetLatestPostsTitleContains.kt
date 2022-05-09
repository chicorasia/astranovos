package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetLatestPostsTitleContains(private val repository: PostRepository) :
    UseCase<Array<String>, List<Post>>() {

    override suspend fun execute(param: Array<String>): Flow<List<Post>> {
        TODO("Not yet implemented")
    }
}