package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte à tela principal (Home).
 */

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    /**
    * Esse campo e as respectivas funções controlam a visibilidade
    * da ProgressBar
    */
    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    /**
     * O campo _listPost agora recebe um objeto do tipo State<List<Post>>
     */
    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchPosts()
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost.
     * Simplesmente adicionar a chave catch { } já evita os crashes
     * da aplicação quando em modo avião.
     */
    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts()
                .onStart {
                    _listPost.postValue(State.Loading)
                }.catch {
                    _listPost.postValue(State.Error(it))
                }
                .collect {
                    _listPost.postValue(State.Success(it))
                }
        }
    }

    /**
     * Posso configurar esse campo de texto para exibir mensagens
     * caso não haja nenhum Post para ler.
     */
    val helloText = Transformations.map(listPost) {
        listPost.let {
            when(it.value) {
                State.Loading -> { "🚀 Loading latest news..."}
                is State.Error -> { "Could not load news. Sorry :'("}
                else -> {""}
            }
        }
    }

}
