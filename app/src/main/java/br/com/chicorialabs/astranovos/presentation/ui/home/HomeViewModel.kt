package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.core.State
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
     * Esse campo armazena a categoria atual.
     * É inicializado com a categoria "articles" como padrão.
     */
    private val _category = MutableLiveData<SpaceFlightNewsCategory>().apply {
        value = SpaceFlightNewsCategory.ARTICLES
    }
    private val category: LiveData<SpaceFlightNewsCategory>
        get() = _category

    /**
     * Esse campo usa uma transformação para gerar o título
     * da toolbar concatenando Latest e a descrição
     * da categoria.
     * TODO: extrair a string hardcoded para um resource
     */
    val toolbarTitle = Transformations.map(category){
        "Latest ${it.description}"
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
        fetchPosts("articles")
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost.
     * Simplesmente adicionar a chave catch { } já evita os crashes
     * da aplicação quando em modo avião.
     */
    private fun fetchPosts(param: String) {
        viewModelScope.launch {
            getLatestPostUseCase(param)
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

    fun fetchLatest(category: SpaceFlightNewsCategory) {
        fetchPosts(category.value)
        _category.value = category
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
