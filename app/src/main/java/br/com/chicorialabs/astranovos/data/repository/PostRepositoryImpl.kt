package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.Resource
import br.com.chicorialabs.astranovos.core.networkBoundResource
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.entities.db.toModel
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.entities.network.PostDTO
import br.com.chicorialabs.astranovos.data.entities.network.toDb
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Essa classe implementa a interface PostRepository. Os dados são retornados na forma de um flow.
 * A responsabilidade de converter entre DTO e entidade de modelo cabe a esta classe.
 * O acesso à API é intermediado por um networkBoundResource.
 */

class PostRepositoryImpl(
    private val service: SpaceFlightNewsService,
    private val dao: PostDao
) : PostRepository {

    /**
     * Esse bloco executa uma consulta ao armazenamento interno, retornando os resultados
     * ordenados por data de publicação (mais novos no início). Ele é passado como parâmetro
     * para o networkBoundResource.
     */
    private val readFromDatabase = {
        dao.listPosts().map { list ->
            list.sortedBy { post ->
                post.publishedAt
            }.reversed().toModel()
        }
    }

    /**
     * Esse bloco executa uma limpeza da database para permitir o refresh da cache;
     * ele é passado como parâmetro de networkBoundResource.
     */
    private val clearDbAndSave: suspend (List<PostDTO>) -> Unit = {
        dao.clearDb()
        dao.saveAll(it.toDb())
    }

    /**
     * Essa função de suspensão utiliza o networkboundresouurce para fazer uma consulta
     * à API, retornando as postagens mais recentes da categoria informada. Retorna um Flow
     * de Resource<List<Post>> ou lança uma RemoteException casa ocorra erro na requisição.
     * @param category: categoria desejada (article, blog ou post) na forma de String
     */
    override suspend fun listPosts(category: String): Flow<Resource<List<Post>>> =
        networkBoundResource(
            query = readFromDatabase,
            fetch = { service.listPosts(category) },
            saveFetchResult = { listPostDto ->
                clearDbAndSave(listPostDto)
            },
            onError = { RemoteException("Could not connect to SpaceFlightNews. Displaying cached content.") }
        )

    /**
     * Essa função de suspensão realiza busca por categoria e string no título da postagem, também
     * com o apoio do networkBoundResource. Retorna um Flow de Resource<List<Post>> para ser tratado
     * no UseCase e ViewModel.
     * @param category: categoria desejada (article, blog ou post) na forma de String
     * @param titleContains: String a buscar no título da postagem
     */
    override suspend fun listPostsTitleContains(
        category: String,
        titleContains: String?
    ): Flow<Resource<List<Post>>> = networkBoundResource(
        query = readFromDatabase,
        fetch = { service.listPostsTitleContains(category, titleContains) },
        saveFetchResult = { listPostDto ->
            clearDbAndSave(listPostDto)
        },
        onError = { RemoteException("Could not connect to SpaceFlightNews. Displaying cached content.") }
    )

}

