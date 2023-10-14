package br.com.chicorialabs.astranovos.data.dao

import androidx.room.*
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface declara os métodos de acesso à database. A implementação
 * concreta fica a cargo do Room.
 */
@Dao
interface PostDao {

    //o método saveAll() recebe uma lista e salva na database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(list: List<PostDb>)

    //aplicando a regra de resolução de conflito post a post,
    //e não à lista inteira
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(post: PostDb)

    //o método listPosts() retorna todos os registros da database
    @Query("SELECT * FROM post WHERE category IS :category")
    fun listPosts(category: String) : Flow<List<PostDb>>

    //clearDb() limpa a database
    @Transaction
    @Query("DELETE FROM post WHERE category IS :category AND isFavourite == 0")
    suspend fun clearDb(category: String)

    @Query("UPDATE post SET isFavourite = NOT isFavourite WHERE id = :postId")
    suspend fun toggleIsFavourite(postId: Int)

    @Query("SELECT * FROM post WHERE id is :postId")
    fun getPostWithId(postId: Int) : PostDb

    @Update
    suspend fun updatePost(post: PostDb)

}