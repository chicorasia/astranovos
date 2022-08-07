package br.com.chicorialabs.astranovos.data.repository

import android.util.Log
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.db.toModel
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.entities.network.toDb
import br.com.chicorialabs.astranovos.data.entities.network.toModel
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

const val TAG = "Astranovos"

/**
 * Essa classe implementa a interface PostRepository com duas
 * dependências:
 * - service: acesso à api web
 * - dao: acesso ao repositório em disco (cache)
 * Os dados são emitidos na forma de um flow.
 */
class PostRepositoryImpl(private val service: SpaceFlightNewsService,
                         private val dao: PostDao
) : PostRepository {

//    /**
//     * Essa função usa o construtor flow { } para emitir a lista de Posts
//     * na forma de um fluxo de dados.
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
//            val postList = service.listPosts(type = category).toModel()
//            emit(postList)
//        } catch (ex: HttpException) {
//            throw RemoteException("Unable to retrieve posts")
//        }
//
//    }

    /**
     * Essa função tenta atualizar a cache local com a categoria recebida
     * via parâmetro e depois recebe um Flow<List<PostDb> do armazenamento
     * local, transformando em Flow<List<Post>> para exibição
     * na tela de Home.
     * @param category: Categoria de postagem na forma de String
     */
    override suspend fun listPosts(category: String): Flow<List<Post>> {

        try {
            refreshCache(category)
        } catch (ex: RemoteException) {
//            throw RemoteException("Error connecting to API.")
            Log.d(TAG, ex.cause.toString())
        }

        return dao.listPosts().map {
            it.toModel()
        }.flowOn(Dispatchers.Main)

    }

    /**
     * Essa função atualiza a cache com dados recebidos da API
     * caso a requisição tenha sucesso.
     */
    private suspend fun refreshCache(category: String) {
        val repositoryScope = CoroutineScope(Dispatchers.IO)
        var newPosts: List<PostDb>? = null

        withContext(Dispatchers.Main) {
            try {
                newPosts = service.listPosts(category).toDb()
            } catch (ex: UnknownHostException) {
                throw RemoteException("Cannot reach API. Displaying only cached posts")
            }
        }

        newPosts?.let {
            repositoryScope.launch {
                dao.clearDb()
                dao.saveAll(it)
            }
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
                titleContains = titleContains).toModel()
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }

    }
}