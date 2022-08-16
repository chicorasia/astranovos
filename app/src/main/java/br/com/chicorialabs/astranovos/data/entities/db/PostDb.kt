package br.com.chicorialabs.astranovos.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import br.com.chicorialabs.astranovos.data.entities.model.Post
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Essa data class representa a entidade Post para gravação na database.
 * Possui um método de conveniência para conversão de entidade de
 * database para uma entidade de modelo.
 */
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

/**
 * Um método de conveniência para converter List<PostDb> em List<Post>
 */
fun List<PostDb>.toModel() : List<Post> =
    this.map {
        it.toModel()
    }

/**
 * Esses conversores permitem armazenar os objetos LaunchDb
 * desserializados como uma String.
 */
class PostDbConverters {

    //TODO: otimizar os campos como atributos de classe
    //TODO: usar kotlinx-serialization para simplificar a conversão

    @TypeConverter
    fun fromString(string: String): Array<LaunchDb>? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Array<LaunchDb>::class.java)
        return jsonAdapter.fromJson(string)
    }

    @TypeConverter
    fun toString(array: Array<LaunchDb>) : String? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Array<LaunchDb>::class.java)
        return jsonAdapter.toJson(array)
    }

}