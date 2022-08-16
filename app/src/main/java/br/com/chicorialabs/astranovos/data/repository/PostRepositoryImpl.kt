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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
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

    /**
     * Essa função tenta atualizar a cache local com a categoria recebida
     * via parâmetro e depois recebe um Flow<List<PostDb> do armazenamento
     * local, transformando em Flow<List<Post>> para exibição
     * na tela de Home.
     * @param category: Categoria de postagem na forma de String
     */
    override suspend fun listPosts(category: String): Flow<List<Post>> {

        runCatching {
            refreshCache(category)
        }.onFailure {
            Log.d(TAG, it.toString())
        }

        return try {
            dao.listPosts().map {
                it.toModel().sortedBy { post ->
                    post.publishedAt
                }.reversed()
            }.flowOn(Dispatchers.Main)
        } catch (ex: IOException) {
            throw IOException("Error loading from local cache.")
        }
    }

    /**
     * Essa função atualiza a cache com dados recebidos da API
     * caso a requisição tenha sucesso, ou lança uma RemoteException
     * caso ocorra alguma falha.
     */
    private suspend fun refreshCache(category: String) {
        val repositoryScope = CoroutineScope(Dispatchers.IO)
        var newPosts: List<PostDb>? = null

        runCatching {
            withContext(Dispatchers.Main) {
                newPosts = service.listPosts(category).toDb()
            }
        }.onFailure {
            throw RemoteException("Could not refresh cache.")
        }.onSuccess {
            newPosts?.let {
                repositoryScope.launch {
                    dao.clearDb()
                    dao.saveAll(it)
                }
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