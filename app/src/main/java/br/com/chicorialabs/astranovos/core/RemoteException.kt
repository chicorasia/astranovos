package br.com.chicorialabs.astranovos.core

/**
 * Essa classe representa uma exceção no acesso aos serviços web.
 * Seu principal ponto de aplicaçõa é a classe PostRepositoryImpl.
 *
 * @param message uma mensagem de erro personalizada.
 */
class RemoteException(message: String) : Exception(message)