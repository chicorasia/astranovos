package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.domain.GetLatestPostsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte à tela principal (Home).
 */
//  TODO 004: Modificar o método fetchPosts() para receber um parâmetro query do tipo String
//  TODO 005: Criar um método público fetchLatest()
//  TODO 007: Criar um campo _category no HomeViewModel
//  TODO 008: Modificar o método init{ } para iniciar com notícias
//  TODO 009: Criar um BindingAdapter para o título da ToolBar
//  TODO 011: modificar o método fetchLatest() para atualizar _category

class HomeViewModel(private val getLatestPostUseCase: GetLatestPostsUseCase) : ViewModel() {

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
     * Esse campo controla a exibição de um snackbar com mensagem de erro na
     * tela do HomeFragment.
     */
    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?>
        get() = _snackbar

    /**
     * Reseta o valor de _snackbar após a mensagem ter sido exibida
     */
    fun onSnackBarShown() {
        _snackbar.value = null
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
            getLatestPostUseCase(SpaceFlightNewsCategory.BLOGS.value)
                .onStart {
                    _listPost.postValue(State.Loading)
                    delay(800) //apenas cosmético
                }.catch {
                    with(RemoteException("Could not connect to SpaceFlightNews API")) {
                        _listPost.postValue(State.Error(this))
                        _snackbar.value = this.message
                    }
                }
                .collect {
                    _listPost.postValue(State.Success(it))
                }
        }
    }

    /**
     * Esse campo exibe uma mensagem na tela inicial conforme o estado
     * de listPost.
     */
    val helloText = Transformations.map(listPost) {
        listPost.let {
            when(it.value) {
                State.Loading -> { "🚀 Loading latest news..."}
                is State.Error -> { "Houston, we've had a problem! :'("}
                else -> {""}
            }
        }
    }

}
