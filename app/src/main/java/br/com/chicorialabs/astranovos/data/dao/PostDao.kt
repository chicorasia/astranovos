package br.com.chicorialabs.astranovos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    //o método listPosts() retorna todos os registros da database
    @Query("SELECT * FROM post WHERE category IS :category")
    fun listPosts(category: String) : Flow<List<PostDb>>

    //clearDb() limpa a database
    @Query("DELETE FROM post WHERE category IS :category")
    suspend fun clearDb(category: String)

}