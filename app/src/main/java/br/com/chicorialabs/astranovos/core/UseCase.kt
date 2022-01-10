package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe abstrata representa um use case genérico.
 * Por enquanto é apenas um use case sem parâmetros.
 */
abstract class UseCase<Source> {

    abstract suspend fun execute() : Flow<Source>

}