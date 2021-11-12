package br.com.chicorialabs.astranovos.data.model

/**
 * Essa data class representa uma publicação; os artigos, postagens
 * em blog e relatórios possuem a mesma estrutura, então deve
 * funcionar em qualquer um dos casos.
 */
data class Post(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: Array<Launch> = emptyArray()
) {
    /**
     * A função hasLaunch() é um método de conveniência para
     * indicar se há lançamentos associados à notícia.
     */
    fun hasLaunch(): Boolean = launches.isNotEmpty()

    fun getLaunchCount() : Int = launches.size

    /**
     * Sobrescrevi os métodos equals() e hashCode() para melhorar o
     * desempenho da DiffUtil
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        return result
    }

}
