package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe abstrata representa uma definição genérica de
 * caso de uso.
 */
abstract class UseCase<Param, Source> {

    abstract suspend fun execute() : Flow<Source>

//    suspend operator fun invoke(param: Param) = execute(param)


}