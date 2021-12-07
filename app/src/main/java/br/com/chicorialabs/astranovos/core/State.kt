package br.com.chicorialabs.astranovos.core

/**
 * Essa classe selada representa os três estados possíveis do Flow: carregando, erro
 * e sucesso. Usar os generics provê segurança de tipo e deixa essa classe muito
 * mais versátil!
 */
sealed class State<out T: Any> {

    /**
     * O estado de Loading pode ser um object porque não possui atributos.
     */
    object Loading : State<Nothing>()

    /**
     * Os casos de Success e Error possuem atributos, então é necessário
     * criá-los como data classes. O atributo é modificado conforme o estado:
     * uma List<Repo> no caso de sucesso e um Throwable no caso de falha.
     * @param result O objeto esperado da consulta ao endpoint
     */
    data class Success<out T: Any>(val result: T) : State<T>()

    data class Error(val error: Throwable) : State<Nothing>()
}



