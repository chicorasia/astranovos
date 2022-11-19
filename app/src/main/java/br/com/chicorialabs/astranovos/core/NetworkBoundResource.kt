package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <RequestType, ResultType>networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onError: (Throwable) -> Throwable =
        { RemoteException("An error has occurred.") }
): Flow<Resource<ResultType>> = flow {

    var data: ResultType = query().first()

    try {
        saveFetchResult(fetch())
        data = query().first()
    } catch (ex: Exception) {
        emit(Resource.Error<ResultType>(data, onError(ex)))
    }
    emit(Resource.Success<ResultType>(data))
}