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
 */
class PostRepositoryImpl(
    private val service: SpaceFlightNewsService,
    private val dao: PostDao
) : PostRepository {

    private val readFromDatabase: (String) -> Flow<List<Post>> = { category: String ->
        dao.listPosts(category).map {
            it.sortedBy { postDb ->
                postDb.publishedAt
            }.reversed().toModel()
        }
    }

    private val clearDbAndSave: suspend (List<PostDTO>, String) -> Unit = { list: List<PostDTO>,
                                                                            category: String ->
        dao.clearDb(category)
//        list.forEach {
//            dao.save(it.toDb(category))
//        }
        dao.saveAll(list.toDb(category))
    }

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados. Ele recebe os dados como PostDTO
     * e invoca o método de conveniência para fazer a conversão em entidade de modelo.
     * @param category Categoria de postagem (article, blog ou post) no formato de String.
     */
    override suspend fun listPosts(category: String): Flow<Resource<List<Post>>> =
        networkBoundResource(
            //Query(category),
            query = { readFromDatabase(category) },
            fetch = { service.listPosts(category) },
            saveFetchResult = { list ->
                clearDbAndSave(list, category)
            },
            onError = { RemoteException("Could not connect to SpaceFlightNews. Displaying cached content.") }
        )

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados.
     * @param category Categoria de postagem (article, blog ou post) no formato de String.
     * @param titleContains String de busca nos títulos de publicação
     */
    override suspend fun listPostsTitleContains(
        category: String,
        titleContains: String?
    ): Flow<Resource<List<Post>>> = networkBoundResource(
        query = { readFromDatabase(category) },
        fetch = { service.listPostsTitleContains(category, titleContains) },
        saveFetchResult = { list ->
            clearDbAndSave(list, category)
        },
        onError = { RemoteException("Could not connect to SpaceFlightNews. Displaying cached content.") }
    )

    override suspend fun toggleIsFavourite(postId: Int) {
        dao.toggleIsFavourite(postId)
    }

    /**
     * Ao menos o primeiro método vai ser útil para o caso de uso de leitura da postagem.
     */
//    override fun getPostWithId(postId: Int): PostDb = dao.getPostWithId(postId)
//
//    override suspend fun updatePost(postDb: PostDb) {
//        dao.updatePost(postDb)
//    }


}




