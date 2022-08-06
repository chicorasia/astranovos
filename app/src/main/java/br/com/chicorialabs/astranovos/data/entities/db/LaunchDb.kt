package br.com.chicorialabs.astranovos.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.chicorialabs.astranovos.data.entities.model.Launch

/**
 * Essa data class representa um objeto Launch para ser armazenado na database.
 * Possui um método de conveniência para conversão em entidade de model.
 */
@Entity(tableName = "launch")
data class LaunchDb(
    @PrimaryKey
    val id: String,
    val provider: String
) {

    fun toModel() : Launch = Launch(
        id = id,
        provider = provider
    )

}

fun Array<LaunchDb>.toModel() : Array<Launch> =
    this.map {
        it.toModel()
    }.toTypedArray()
