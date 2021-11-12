package br.com.chicorialabs.astranovos.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.chicorialabs.astranovos.databinding.HomeFragmentBinding
import br.com.chicorialabs.astranovos.presentation.adapter.PostListAdapter

// Tarefas do PresentationModule
// TODO 003: Criar um pacote di na camada de presentation
// TODO 004: Criar um object PresentationModule dentro do pacote di
// TODO 005: Criar uma função privada viewModelModule()
// TODO 006: Criar uma função load()

// Tarefas da classe App
// TODO 007: Criar uma classe App
// TODO 008: Adicionar o App ao manifest.xml

/**
 * Essa classe representa o fragmento da tela Home.
 */
class HomeFragment : Fragment() {

//    TODO 010: Usar o delegate Koin para injetar o HomeViewModel

    private lateinit var viewModel: HomeViewModel
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        TODO 009: Eliminar essa inicialização manual do ViewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        initBinding()
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {

        val adapter = PostListAdapter()
        binding.homeRv.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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