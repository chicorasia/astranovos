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

abstract class UseCase<Source> {

    abstract suspend fun execute() : Flow<Source>

}