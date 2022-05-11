package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

//TODO 011: Reescrever GetLatestPostUseCase para receber objetos do tipo Query como Param
class GetLatestPostsUseCase(private val repository: PostRepository) : UseCase<String, List<Post>>() {

    override suspend fun execute(param: String): Flow<List<Post>> = repository.listPosts(param)


}