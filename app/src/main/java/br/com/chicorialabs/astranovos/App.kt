package br.com.chicorialabs.astranovos

import android.app.Application
import br.com.chicorialabs.astranovos.data.di.DataModule
import br.com.chicorialabs.astranovos.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Essa classe representa um ponto de entrada no aplicativo.
 * Sua principal função é inicializar o Koin e carregar os
 * módulos.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        /**
         * Carregar os módulos definidos para cada camada.
         * (Outros módulos devem ser adicionados aqui)
         */
        PresentationModule.load()
        DataModule.load()
//        TODO 014: Incluir o DomainModule na classe App
    }
}