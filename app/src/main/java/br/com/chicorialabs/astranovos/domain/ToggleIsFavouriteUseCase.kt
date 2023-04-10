package br.com.chicorialabs.astranovos.domain

import br.com.chicorialabs.astranovos.core.UseCase
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Esse caso de uso alterna o valor do campo isFavourite de um Post armazenado
 * na database e retorna um Flow<Boolean>.
 */
class ToggleIsFavouriteUseCase(private val repository: PostRepository) : UseCase<Int, Boolean>() {

    /**
     * @param param: id do Post no formato Int
     */
    override suspend fun execute(param: Int): Flow<Boolean> = flow {
        emit(repository.toggleIsFavourite(param))
    }
}
