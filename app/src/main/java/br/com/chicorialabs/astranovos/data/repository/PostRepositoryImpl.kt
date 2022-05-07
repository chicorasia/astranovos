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
     * @param category Categoria de postagem (article, blog ou post) no formato de String.
     */
    override suspend fun listPosts(category: String): Flow<List<Post>> = flow {

        /**
         * Tenta obter uma lista lista de Posts e emitir como um flow<List<Post>>
         * Se ocorrer uma exceção no acesso Http joga uma NetworkException.
         * Essa exceção precisa ser tratada no ViewModel.
         */
        try {
            val postList = service.listPosts(type = category)
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }

    }

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados.
     * @param category Categoria de postagem (article, blog ou post) no formato de String.
     * @param titleContains String de busca nos títulos de publicação
     */
    override suspend fun listPostsTitleContains(
        category: String,
        titleContains: String?
    ): Flow<List<Post>> = flow {

        try {
            val postList = service.listPostsTitleContains(
                type = category,
                titleContains = titleContains)
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }

    }
}