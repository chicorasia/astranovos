package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe abstrata representa uma definição genérica de
 * caso de uso.
 * @param Param Parâmetro de entrada da query
 * @param Source Tipo de retorno esperado (vai ser convertido em Flow)
 */
abstract class UseCase<Param, Source> {

    /**
     * Esse é o método padrão, recebendo um parâmetro e retornando
     * um objeto do tipo Flow<Source>
     */
    abstract suspend fun execute(param: Param) : Flow<Source>

    /**
     * Uma otimização usando o modificador operator
     * para simplificar a sintaxe no ponto de uso
     */
    suspend operator fun invoke(param: Param) = execute(param)

    /**
     * Essa subclasse permite criar UseCases para queries
     * que não possuem parâmetros (como a query mais básica da aplicação).
     * Para que funcione passamos o object None no lugar de Param.
     */
    abstract class NoParam<Source> : UseCase<None, Source>() {

        /**
         * Método padrão para uma query sem parâmetros. Esse método não
         * sobrescreve o execute() definido na classe mãe!
         */
        abstract suspend fun execute() : Flow<Source>

        /**
         * Como essa classe herda de UseCase, sou obrigado a implementar
         * o método execute() com parâmetro. Nesse caso, uso o modificador
         * final para travar o método. A função retorna uma exception.
         */
        final override suspend fun execute(param: None): Flow<Source> {
            throw UnsupportedOperationException()
        }

        /**
         * Uma otimização da chamada usando "operator"
         */
        suspend operator fun invoke() = execute()

    }

    /**
     * Um objeto vazio somente para possibilitar a criação da classe
     * NoParam como filha de UseCase com a mesma sintaxe.
     */
    object None

}


