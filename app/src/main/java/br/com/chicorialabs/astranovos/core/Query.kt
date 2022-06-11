package br.com.chicorialabs.astranovos.core

/**
 * Essa data class encapsula os parâmetros de busca na API. Por enquanto ela
 * possui dois parâmetros:
 * @param type : o tipo de postagem no formato de String
 * @param option : um parâmetro opcional no formato de String
 */
data class Query(
    val type: String,
    val option: String? = null
)
