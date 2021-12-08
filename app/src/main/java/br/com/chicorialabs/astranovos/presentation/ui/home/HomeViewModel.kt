package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte à tela principal (Home).
 */

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    // TODO 005: Criar um campo _progressBarVisible
    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    // TODO 006: Criar um campo _snackbar
    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?>
        get() = _snackbar

    fun onSnackBarShown() {
        _snackbar.value = null
    }

    // TODO 004: Modificar o campo _listPost para usar a classe State
    // TODO 014: Modificar o tipo do campo _listPost para usar a classe State<List<Post>>
    /**
     * Agora o campo _listPost espera um objeto do tipo State
     * (sendo que esse objeto carrega dentro de si um resultado
     * do tipo List<Post>>)
     */
    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchPosts()
    }

    /**
     * Esse método inicia a chamada à API via repository e
     * modifica o valor de _listPost conforme o estado da requisição.
     * Como estou trabalhando com um Flow<T>, temos três situações
     * para lidar:
     * - onStart{ } : inicia a conexão
     * - catch{ } : faz o tratamento de exceções
     * - collect { }: coleta os dados quando a conexão é bem-sucedida
     */
    // TODO 007: Modificar o método fetchPosts()
    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts()
                .onStart {
                    _listPost.postValue(State.Loading)
                    delay(800) // apenas para efeito cosmético
                }
                .catch {
                    val exception = RemoteException("Unable to connect to SpaceFlight News API")
                    _listPost.postValue(State.Error(exception))
                    _snackbar.postValue(exception.message)
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
//    TODO 011: Usar uma transformação para modificar o campo helloText
    val helloText = Transformations.map(listPost) {
        when(it) {
            State.Loading -> { "🚀 Loading latest news..."}
            is State.Error -> { "Houston, we've had a problem! :'("}
            else -> {""} //uma string vazia
        }
    }
}
