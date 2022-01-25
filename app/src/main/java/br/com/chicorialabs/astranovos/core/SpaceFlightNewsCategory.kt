package br.com.chicorialabs.astranovos.core

/**
 * Essa enum representa três categorias principais
 * de postagens na API.
 */
enum class SpaceFlightNewsCategory(val value: String, val description: String) {

    ARTICLES("articles", "News"),
    BLOGS("blogs", "Blog Posts"),
    REPORTS("reports", "Mission Reports")

}