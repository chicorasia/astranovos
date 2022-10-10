package br.com.chicorialabs.astranovos.core

/**
 * Encapsula os dados, o estado e eventual erro, permitindo o acesso
 * direto aos dados.
 */
sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {

    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(data: T? = null, t: Throwable) : Resource<T>(data, t)

}


