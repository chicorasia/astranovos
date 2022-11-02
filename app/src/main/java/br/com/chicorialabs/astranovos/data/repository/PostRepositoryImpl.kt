package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.Resource
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.db.toModel
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.entities.network.toDb
import br.com.chicorialabs.astranovos.data.entities.network.toModel
import br.com.chicorialabs.astranovos.data.services.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

/**
 * Essa classe implementa a interface PostRepository. Os dados são retornados na forma de um flow.
 * A responsabilidade de converter entre DTO e entidade de modelo cabe a esta classe.
 */
//TODO 002: Adicionar o dao como dependência do PostRepositoryImpl
//TODO 008: Escrever um novo método listPosts() em PostRepositoryImpl
//TODO 009: Criar uma função networkBoundResource()
//Parte B
//TODO 011: Extrair o bloco query()
//TODO 012: Extrair o bloco fetch()
//TODO 013: Extrair o bloco saveFetchResult()
//TODO 014: Extrair uma função onError()
//TODO 015: Adotar generics no networkBoundResource
//TODO 016: transformar networkBoundResource em função inline
//Parte C
//TODO 019: Modificar o método listPostsTitleContains() para empregar o networkBoundResource()
//TODO 021: Refatorar listPosts() e listPostsTitleContains()
//TODO 022: Mover networkBoundResource() para um arquivo à parte no pacote core
class PostRepositoryImpl(private val service: SpaceFlightNewsService,
                         private val dao: PostDao
                         ) : PostRepository {

    override suspend fun listPosts(category: String): Flow<Resource<List<Post>>> =
        networkBoundResource(category)



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
                titleContains = titleContains).toModel()
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts")
        }

    }

    private fun networkBoundResource(category: String): Flow<Resource<List<Post>>> = flow {

        var data: List<PostDb> = dao.listPosts().first()

        try {
            with(service.listPosts(category)) {
                if(this.isNotEmpty()) {
                    dao.clearDb()
                    dao.saveAll(this.toDb())
                    data = dao.listPosts().first()
                }
            }
        } catch (ex: Exception) {
            val error = RemoteException("Error connecting to API. Displaying cached content.")
            emit(Resource.Error<List<Post>>(data.toModel(), error))
        }
        emit(Resource.Success(data.toModel()))
    }




}