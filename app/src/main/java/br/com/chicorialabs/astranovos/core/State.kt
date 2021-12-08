package br.com.chicorialabs.astranovos.core

import br.com.chicorialabs.astranovos.data.model.Post

/**
 * Essa classe representa os três estados de uma requisição à API:
 * - Loading (sem parâmetros)
 * - Success (com a resposta como parâmetro)
 * - Error (com o erro como parâmetro)
 */
sealed class State {

    /**
     * Loading não possui atributos por isso pode ser um object
     */
    object Loading: State()

    /**
     * Success e Error possuem atributos, então é melhor criá-los como
     * data classes. O atributo é modificado conforme o estado:
     * uma List<Repo> no caso de sucesso e um Throwable no caso de falha.
     */
    data class Success(val result: List<Post>) : State()

    data class Error(val error: Throwable) : State()

}