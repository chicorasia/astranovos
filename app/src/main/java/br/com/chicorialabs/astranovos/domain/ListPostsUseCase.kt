package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class ListPostsUseCase(private val repository: PostRepository) : UseCase.NoParam<List<Post>>() {

    override suspend fun execute(): Flow<List<Post>> = repository.listPosts()

    /**
     * Esse override seria necessário se não tivesse uma função final
     * definida na classe UseCase.NoParam
     */
//    override suspend fun execute(param: None): Flow<List<Post>> {
//        throw UnsupportedOperationException()
//    }


}