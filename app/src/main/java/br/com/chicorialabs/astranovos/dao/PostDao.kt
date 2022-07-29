package br.com.chicorialabs.astranovos.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun listPosts() : Flow<List<PostDao>>

    @Query("SELECT * FROM post WHERE title LIKE :searchString")
    fun listPostsTitleContains(searchString: String) : Flow<List<PostDao>>

}