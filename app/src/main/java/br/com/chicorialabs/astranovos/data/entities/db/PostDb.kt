package br.com.chicorialabs.astranovos.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.chicorialabs.astranovos.data.entities.model.Post

/**
 * Essa data class é usada para o armazenamento de postagens da database interna.
 */
//TODO 008: Criar uma data class PostDb ✅
//TODO 009: Anotar PostDb com @Entity e definir uma @PrimaryKey ✅

@Entity(tableName = "post")
data class PostDb(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    //Preciso resolver o armazenamento desse array...
    var launches: Array<LaunchDb> = emptyArray()
) {

    fun toModel() : Post = Post(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        launches = launches.toModel()
    )
}

//TODO 010: Criar um método de conveniência para converter List<PostDb> em List<Post> ✅
/**
 * Uma função de extensão para converter uma lista de PostDb em lista de Post
 */
fun List<PostDb>.toModel() : List<Post> =
    this.map {
        it.toModel()
    }