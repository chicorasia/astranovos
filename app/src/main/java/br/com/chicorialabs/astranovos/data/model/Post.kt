package br.com.chicorialabs.astranovos.data.model

/**
 * Essa data class representa uma publicação; os artigos, postagens
 * em blog e relatórios possuem a mesma estrutura, então deve
 * funcionar em qualquer um dos casos.
 * A função hasLaunch() é um método de conveniência para
 * indicar se há lançamentos associados à notícia.
 */
data class Post(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    val launches: Array<Launch> = emptyArray()
) {
    fun hasLaunch(): Boolean = launches.isEmpty()

}
