package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.data.model.Launch
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

/**
 * Essa classe implementa a interface PostRepository, inicialmente
 * usando um serviço mockado. Os dados são retornados na forma de um flow.
 */
class PostRepositoryImpl(private val service: SpaceFlightNewsService) : PostRepository {

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados. Aqui a função acessa um serviço
     * mockado. No uso real é preciso usar um bloco try-catch para
     * lidar com exceções no acesso à API.
     */
    override suspend fun listPosts(): Flow<List<Post>> = flow {

        /**
         * Recebe uma lista de Posts e emite como um flow<List<Post>>
         */
        val postList = service.listPosts()
        emit(postList)

    }
}