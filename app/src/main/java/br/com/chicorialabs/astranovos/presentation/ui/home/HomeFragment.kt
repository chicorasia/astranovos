package br.com.chicorialabs.astranovos.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.databinding.HomeFragmentBinding
import br.com.chicorialabs.astranovos.presentation.adapter.PostListAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initBinding()

        // TODO 009: Criar um observer para o Snackbar
        initSnackbar()
        initRecyclerView()
        return binding.root
    }

    /**
     * Essa função configura um observer para o campo snackbar
     * e exibe uma mensagem na parte de baixo da tela quando o campo
     * não é nulo.
     * Depois, chama a função onSnackBarShown() para resetar o valor
     * do campo.
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

        // TODO 008: Modificar o observer de listPost para usar os States
        /**
         * Observar o campo listPost e manipular a UI conforme
         * o seu estado
         */
        viewModel.listPost.observe(viewLifecycleOwner) {
            when(it) {
                State.Loading -> {
                    viewModel.showProgressBar() //mostra a ProgressBar
                }
                is State.Error -> {
                    viewModel.hideProgressBar() //oculta a ProgressBar
                }
                is State.Success -> {
                    viewModel.hideProgressBar() // oculta a ProgressBar
                    adapter.submitList(it.result) //submete a lista vinculada ao State
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