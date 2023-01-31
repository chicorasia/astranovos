package br.com.chicorialabs.astranovos.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import br.com.chicorialabs.astranovos.data.entities.model.Post
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Essa data class é usada para o armazenamento de postagens da database interna.
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
    var launches: Array<LaunchDb> = emptyArray(),
    val category: String
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
 * Uma função de extensão para converter uma lista de PostDb em lista de Post
 */
fun List<PostDb>.toModel() : List<Post> =
    this.map {
        it.toModel()
    }

class PostDbConverters {

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