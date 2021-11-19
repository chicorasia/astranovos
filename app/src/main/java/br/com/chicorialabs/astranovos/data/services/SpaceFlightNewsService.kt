package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import retrofit2.http.GET

/**
 * Essa interface define como o Retrofit vai conversar com a
 * SpaceFlightNews API usando as requisições HTTP.
 * https://api.spaceflightnewsapi.net/v3/documentation
 */

interface SpaceFlightNewsService {

    /**
     * Esse endpoint acessa a lista de artigos (notícias); vamos
     * começar com uma chamada sem parâmetros. Usar uma função de suspensão
     * para que as chamadas à API ocorram fora da thread principal.
     */
    // TODO 012: Adicionar o endpoint '/articles'
    @GET("articles")
    suspend fun listPosts() : List<Post>

}