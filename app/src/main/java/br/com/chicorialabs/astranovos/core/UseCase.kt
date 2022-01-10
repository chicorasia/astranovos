package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe abstrata representa um use case genérico.
 * Por enquanto é apenas um use case sem parâmetros.
 */

//TODO 001: Modificar a assinatura de UseCase para ter Param e Source
//TODO 002: Criar uma classe abstrata NoParam que herda de UseCase
//TODO 003: Criar um object None
//TODO 004: Adicionar uma função execute() na subclasse NoParam.
//TODO 006: Fazer um final override de execute(param: None)
//TODO 007: Adicionar uma função operator invoke()

abstract class UseCase<Param, Source> {

    /**
     * Esse é o método padrão, recebendo um parâmetro e retornando
     * um objeto do tipo Flow<Source>
     */
    abstract suspend fun execute(param: Param): Flow<Source>

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
        abstract suspend fun execute(): Flow<Source>

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