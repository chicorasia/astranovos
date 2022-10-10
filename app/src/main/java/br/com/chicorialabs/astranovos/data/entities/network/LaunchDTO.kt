package br.com.chicorialabs.astranovos.data.entities.network

import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
import br.com.chicorialabs.astranovos.data.entities.model.Launch


/**
 * Essa data class representa um objeto Lanuch recebido da API e serve como
 * entidade intermediária entre a API e o app.
 * Ela complementa PostDTO.
 */
data class LaunchDTO(
    val id: String,
    val provider: String
) {

    fun toModel(): Launch = Launch(
        id = id,
        provider = provider
    )

    fun toDb() : LaunchDb = LaunchDb(
        id = id,
        provider = provider
    )

}

/**
 * Um método de conveniência para converter um Array<LaunchDTO> em um
 * Array<Launch>.
 */
fun Array<LaunchDTO>.toModel() : Array<Launch> =
    this.map {
        it.toModel()
    }.toTypedArray()

fun Array<LaunchDTO>.toDb() : Array<LaunchDb> =
    this.map {
        it.toDb()
    }.toTypedArray()