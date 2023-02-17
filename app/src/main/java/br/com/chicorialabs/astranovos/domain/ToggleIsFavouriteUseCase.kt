package br.com.chicorialabs.astranovos.domain

import android.util.Log
import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToggleIsFavouriteUseCase(private val repository: PostRepository) : UseCase<Int, Boolean>() {

    override suspend fun execute(param: Int): Flow<Boolean> = flow {
        Log.i("AstraNovos", "execute: Calling ToggleIsFavouriteUseCase for post id $param")

        val post = repository.getPostWithId(param)
        val newPost = post.copy(isFavourite = !post.isFavourite)
        repository.updatePost(newPost)
        Log.i("AstraNovos", "Trying to save post with id ${newPost.id} and isFavourite ${newPost.isFavourite} in the database.")
        emit(true)
//        with(repository.getPostWithId(param)) {
//            val post = this.copy(
//                isFavourite = !this.isFavourite
//            )
//            Log.i("AstraNovos", "Trying to save post with id ${post.id} and isFavourite ${post.isFavourite} in the database.")
//            repository.updatePost(post)
//            emit(post.isFavourite)
//        }
    }
}
