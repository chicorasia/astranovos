package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.network.PostDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Essa interface define como o Retrofit vai conversar com a
 * SpaceFlightNews API usando as requisições HTTP.
 * https://api.spaceflightnewsapi.net/v3/documentation
 * Foi adotado o padrão DTO de maneira que o serviço
 * retorna listas de PostDTO, cabendo ao repositório
 * a responsabilidade de converter em List<Post> antes de
 * expor os dados para a UI.
 */

interface SpaceFlightNewsService {


    /**
     * Esse endpoint acessa a lista de artigos (notícias); vamos
     * começar com uma chamada sem parâmetros. Usar uma função de suspensão
     * para que as chamadas à API ocorram fora da thread principal.
     * Emprega o padrão Data Transfer Object para desvincular a camada de
     * dados da API externa.
     */
    @GET("{type}")
    suspend fun listPosts(@Path("type") type: String) : List<PostDTO>

    /**
     * Um método de busca avançada com dois parâmetros
     */
    @GET("{type}")
    suspend fun listPostsTitleContains(@Path("type") type: String,
                                       @Query("title_contains") titleContains: String?) : List<PostDTO>

}