package br.com.chicorialabs.astranovos.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import br.com.chicorialabs.astranovos.R
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.databinding.HomeFragmentBinding
import br.com.chicorialabs.astranovos.presentation.adapter.PostListAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Essa classe representa o fragmento da tela Home.
 */
class HomeFragment : Fragment() {

    /**
     * Usa o Koin para injetar a dependência do ViewModel
     */
    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    /**
     * Cria um objeto SearchView como uma variável
     * de inicialização tardia para permitir a vinculação
     * com um campo observável
     */
    private lateinit var searchView: SearchView

    /**
     * Inicializa e infla o option menu no momento
     * da criação do fragmento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOptionMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initBinding()
        initSnackbar()
        initRecyclerView()
        return binding.root
    }

    /**
     * Vincula o "hint" de busca a um campo do tipo LiveData
     * no ViewModel; faço isso depois que a view é criada
     * para evitar problemas durante a inflação.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initQueryHintObserver()
    }

    private fun initQueryHintObserver() {
        viewModel.category.observe(viewLifecycleOwner) {
            searchView.queryHint = "${getString(R.string.search_in)} " + when (it) {
                SpaceFlightNewsCategory.ARTICLES -> getString(R.string.news)
                SpaceFlightNewsCategory.BLOGS -> getString(R.string.blogs)
                SpaceFlightNewsCategory.REPORTS -> getString(R.string.reports)
                null -> throw IllegalArgumentException("Invalid category")
            }
        }
    }

    /**
     * Esse método infla o options menu dentro da Toolbar.
     * Cada item dispara uma chamada ao método fetchLatest()
     * do ViewModel para atualizar a lista com as postagems
     * da categoria escolhida.
     */
    fun initOptionMenu() {
        with(binding.homeToolbar) {
            inflateMenu(R.menu.options_menu)

            menu.findItem(R.id.action_get_articles).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.ARTICLES)
                true
            }

            menu.findItem(R.id.action_get_blogs).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.BLOGS)
                true
            }

            menu.findItem(R.id.action_get_reports).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.REPORTS)
                true
            }

            this.initSearchBar()

        }
    }

    /**
     * Essa função de extensão inicializa o campo de busca dentro da
     * MaterialToolBar
     */
    private fun MaterialToolbar.initSearchBar() {
        // Recupera o item do menu como uma SearchView para dar acesso ao campo query
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        with(searchView) {
            // Abre o campo de busca por padrão
            isIconified = false

            // Configura o listener de mudança no campo
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // extrai a string de busca
                    val searchString = searchView.query.toString()
                    // faz a busca na API
                    viewModel.doSearch(searchString)
                    // esconde o teclado virtual
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // executa buscas a cada modificação no campo
                    newText?.let { viewModel.doSearch(it) }
                    return true
                }

            })
        }
    }

    /**
     * Essa função inicializa o Snackbar. O método onSnackBarShown()
     * reseta o valor do campo do ViewModel após a mensagem ter sido
     * exibida.
     */
    private fun initSnackbar() {
        viewModel.snackbar.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Snackbar.make(
                    binding.root, errorMessage,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onSnackBarShown()
            }
        }
    }

    private fun initRecyclerView() {

        val adapter = PostListAdapter()
        binding.homeRv.adapter = adapter

        /**
         * Observa o campo listPost do HomeViewModel e modifica a
         * UI conforme o seu estado.
         */
        viewModel.listPost.observe(viewLifecycleOwner) {
            when (it) {
                State.Loading -> {
                    viewModel.showProgressBar()
                }
                is State.Error -> {
                    viewModel.hideProgressBar()
                }
                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(it.result)
                }
            }
        }

    }

    /**
     * Esse método faz a inicialização do DataBinding.
     * O arquivo XML possui uma variável viewModel, que precisa
     * ser vinculada ao ViewModel instanciado. Também é preciso
     * atribuir um LifeCycleOwner para que os bindings de live data
     * funcionem.
     */
    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }


    /**
     * Esse companion object é código boilerplate que provavelmente
     * não será usado.
     */
    companion object {
        fun newInstance() = HomeFragment()
    }

}