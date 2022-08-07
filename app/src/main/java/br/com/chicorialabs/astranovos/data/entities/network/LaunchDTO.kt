package br.com.chicorialabs.astranovos.data.entities.network

import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
import br.com.chicorialabs.astranovos.data.entities.model.Launch

/**
 * Essa classe representa um objeto Launch recebido da API.
 * Possui um método de conveniência para conversão em
 * entidade de modelo.
 */
data class LaunchDTO(
    val id: String,
    val provider: String
) {

    //TODO: Escrever testes de unidade
    /**
     * Um método de conveniência para converter em entidade de modelo
     */
    fun toModel() = Launch(
        id = id,
        provider = provider
    )

    fun toDb() : LaunchDb= LaunchDb(
        id = id,
        provider = provider
    )

}

fun Array<LaunchDTO>.toModel() : Array<Launch> =
    this.map {
        it.toModel()
    }.toTypedArray()

fun Array<LaunchDTO>.toDb() : Array<LaunchDb> =
    this.map {
        it.toDb()
    }.toTypedArray()
