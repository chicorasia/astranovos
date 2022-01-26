package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

//TODO 011: Mudar a herança de GetLatestPostsUseCase
//TODO 012: Implementar o método execute(param: String)

class GetLatestPostsUseCase(private val repository: PostRepository) : UseCase.NoParam<List<Post>>() {

    override suspend fun execute(): Flow<List<Post>> = repository.listPosts()

}