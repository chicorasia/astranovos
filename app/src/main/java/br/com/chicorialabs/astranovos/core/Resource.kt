package br.com.chicorialabs.astranovos.core

/**
 * Essa classe selada mantém dois tipos de Resource possíveis - Success e Error - usados
 * para empacotar os resultados das requisições feitas à API por meio do networkBoundResource.
 * Como a aplicação já possui uma classe State com o valor de Loading, não achei necessário ter
 * esse terceiro estado.
 * Mantendo um campo data no estado de Error permite transportar os dados previamente gravados na
 * database mesmo que aconteça um erro na requisição à API.
 *
 * @param data: dados a empacotar; pode ser null.
 * @param error: um Throwable decorrente do erro; pode ser null.
 *
 */
sealed class Resource<T>(
    open val data: T?,
    open val error: Throwable? = null
) {

    data class Success<T>(override val data: T?) : Resource<T>(data, null)

    data class Error<T>(
        override val data: T?,
        override val error: Throwable) : Resource<T>(null, error)

}