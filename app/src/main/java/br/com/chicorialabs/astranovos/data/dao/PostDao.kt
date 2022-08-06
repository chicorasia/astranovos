package br.com.chicorialabs.astranovos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.chicorialabs.astranovos.data.entities.db.PostDb

/**
 * Essa interface define os métodos de acesso ao repositório em disco
 */
@Dao
interface PostDao {

    /**
     * Grava uma lista de PostDb no armazenamento local,
     * ignorando postagens repetidas.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(list: List<PostDb>)

    /**
     * Retorna todas as postagens armazenadas em disco
     */
    @Query("SELECT * FROM post")
    fun listPosts() : List<PostDb>

    /**
     * Limpa a database.
     */
    @Query("DELETE FROM post")
    suspend fun clearDb()

}