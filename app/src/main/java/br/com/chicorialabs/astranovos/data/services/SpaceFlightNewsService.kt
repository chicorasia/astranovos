package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import retrofit2.http.GET

/**
 * Essa interface é responsável pela comunicação com a API web
 */
interface SpaceFlightNewsService {

    /**
     * Esse endpoint acessa uma lista de artigos.
     * Ele não possui parâmetros.
     */
    @GET("articles")
    suspend fun listPosts() : List<Post>

}