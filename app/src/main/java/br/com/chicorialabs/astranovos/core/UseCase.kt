package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe abstrata representa uma definição genérica de
 * caso de uso.
 */
abstract class UseCase<Param, Source> {

    abstract suspend fun execute(param: Param) : Flow<Source>

    abstract class NoParam<Source> : UseCase<None, Source>() {

        abstract suspend fun execute() : Flow<Source>

        /**
         * Como essa classe herda de UseCase, sou obrigado a implementar
         * o método execute() com parâmetro. Nesse caso, uso o modificador
         * final para travar o método. A função retorna uma exception.
         */
        final override suspend fun execute(param: None): Flow<Source> {
            throw UnsupportedOperationException()
        }

        suspend operator fun invoke() = execute()

    }

//    suspend operator fun invoke(param: Param) = execute(param)

    object None

}


