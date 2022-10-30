package br.com.chicorialabs.astranovos.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.chicorialabs.astranovos.data.entities.model.Launch

/**
 * Essa data class representa um evento de lançamento e se destina
 * ao armazenamento na database. Possui método de conveniência para conversão
 * em entidade de modelo.
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

/**
 * Uma função de extensão para converter Array<LaunchDb> em Array<Launch>
 */
fun Array<LaunchDb>.toModel() : Array<Launch> =
    this.map {
        it.toModel()
    }.toTypedArray()
