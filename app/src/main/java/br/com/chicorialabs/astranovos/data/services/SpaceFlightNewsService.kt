package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

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
    @GET("{type}")
    suspend fun listPosts(@Path("type") type: String) : List<Post>

//    TODO 002: Criar o método listPostsTitleContains() no SpaceFlightNewsService

}