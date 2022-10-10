package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.Resource
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.entities.db.toModel
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.entities.network.toDb
import br.com.chicorialabs.astranovos.data.entities.network.toModel
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

/**
 * Essa classe implementa a interface PostRepository. Os dados são retornados na forma de um flow.
 * A responsabilidade de converter entre DTO e entidade de modelo cabe a esta classe.
 */
class PostRepositoryImpl(
    private val service: SpaceFlightNewsService,
    private val dao: PostDao
) : PostRepository {

    override suspend fun listPosts(category: String): Flow<Resource<List<Post>>> = networkBoundResource(
            query = {
                dao.listPosts().map {
                    it.toModel().sortedBy {
                        it.publishedAt
                    }.reversed()
                }
            },
            fetch = {
                service.listPosts(category)
            },
            saveFetchResult = {
                dao.clearDb()
                dao.saveAll(it.toDb())
            }
    )




//    /**
//     * Essa função usa o construtor flow { } para emitir a lista de Posts
//     * na forma de um fluxo de dados. Ele recebe os dados como PostDTO
//     * e invoca o método de conveniência para fazer a conversão em entidade de modelo.
//     * @param category Categoria de postagem (article, blog ou post) no formato de String.
//     */
//    override suspend fun listPosts(category: String): Flow<List<Post>> = flow {
//
//        /**
//         * Tenta obter uma lista lista de Posts e emitir como um flow<List<Post>>
//         * Se ocorrer uma exceção no acesso Http joga uma NetworkException.
//         * Essa exceção precisa ser tratada no ViewModel.
//         */
//        try {
//            //recebe uma List<PostDTO> e converte em List<Post> antes de emitir como fluxo
//            val postList = service.listPosts(type = category).toModel()
//            emit(postList)
//        } catch (ex: HttpException) {
//            throw RemoteException("Unable to retrieve posts")
//        }
//
//
//
//    }

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
                titleContains = titleContains
            ).toModel()
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }

    }
}

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    val data = query().first() //retorna um único valor do fluxo

    val flow = if (shouldFetch(data)) {
        emptyFlow<ResultType>()
        emit(Resource.Loading(data)) //display cached data
        //make network request
        try {
            saveFetchResult(fetch())
            query().map {
                Resource.Success(it)
            }
        } catch (t: Throwable) {
            //tornar isso abstrato
            val ex = RemoteException("Could not connect to the API. Displaying cached content.")
            query().map {
                Resource.Error(data = it, t = ex) //mostra dados em cache + mensagem de erro
            }
        }
    } else {
        query().map {
            Resource.Success(data = it)
        }
    }

    emitAll(flow)
}