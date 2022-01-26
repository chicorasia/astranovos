package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

/**
 * Essa classe implementa a interface PostRepository, inicialmente
 * usando um serviço mockado. Os dados são retornados na forma de um flow.
 */
class PostRepositoryImpl(private val service: SpaceFlightNewsService) : PostRepository {

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados.
     */
//    TODO 013: Atualizar o método listPosts() da classe concreta PostRepositoryImpl
    override suspend fun listPosts(): Flow<List<Post>> = flow {

        /**
         * Tenta obter uma lista lista de Posts e emitir como um flow<List<Post>>
         * Se ocorrer uma exceção no acesso Http joga uma NetworkException.
         * Essa exceção precisa ser tratada no ViewModel.
         */
        try {
            val postList = service.listPosts()
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }

    }
}