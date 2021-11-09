package br.com.chicorialabs.astranovos.presentation.ui.di

import br.com.chicorialabs.astranovos.presentation.ui.home.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * Esse object é responsável por instanciar e configurar os ViewModels
 * exigidos pelos Fragments´, segundo a filosofia de agrupar os módulos
 * por camadas.
 */
object PresentationModule {

    /**
     * Essa função é que fica exposta publicamente para ser chamada
     * na classe App. É possível concatenar vários módulos usando o
     * operador "+". Isso reduz a duplicação de código na classe App.
     */
    fun load() {
        loadKoinModules(viewModelModule())
    }

    /**
     * Instancia os Viewmodels da camada de apresentação
     */
    private fun viewModelModule() : Module {
        return module {
            factory { HomeViewModel() }
        }
    }


}