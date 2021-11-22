package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.MockAPIService
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.StringBuilder

/**
 * Essa classe dá suporte à tela principal (Home).
 * Recebe uma interface PostRepository como dependência,
 * resolvida por meio do DataModule do Koin.
 */

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    private val _listPost = MutableLiveData<List<Post>>()
    val listPost: LiveData<List<Post>>
        get() = _listPost

    init {
        fetchPosts()
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost
     */
    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts().collect {
                _listPost.value = it
            }
        }
    }

    /**
     * Posso configurar esse campo de texto para exibir mensagens
     * caso não haja nenhum Post para ler.
     */
    val helloText = StringBuilder().apply{
        _listPost.value?.let { list ->
            append("There are ${list.size} posts:")
            appendLine()
            list.forEach { post ->
                appendLine("--- start post ---")
                append("${post.title}")
                appendLine()
                append("${post.summary}")
                appendLine()
            }

        }
    }
}
