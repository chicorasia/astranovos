package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Essa função opera a mágica da cache. Ela usa o construtor de flow { } para encadear várias
 * operações, iniciando pela consulta à database local com `query()`, atribuindo o valor para
 * o campo `data` do objeto Resource<ResultType>; dentro do bloco try-catch é realizada uma
 * consulta à API remota com `fetch()`, cujo resultado é passado imediatamente para
 * `saveFetchResult()`, que define o tratamento dos dados recebidos. Em caso de erro é emitido um
 * Resource.Error<ResultType> com os dados previamente carregados e o erro ocorrido.
 * Em caso de sucesso é emitido um Resource.Success<ResultType> com os dados e sem atributo de erro.
 * Os campos da função devem ser definidos no ponto de utilização, sendo passados como blocos
 * de código. O uso de generics garante segurança de tipo.
 * @param RequestType: o tipo do objeto recebido da API
 * @param ResultType: tipo de objeto esperado pelo modelo
 * @param query: operação de busca na database local
 * @param fetch: operação de busca na API remota
 * @param saveFetchResult: operação de gravação na database local
 * @param onError: o que fazer em caso de erro na requisição
 */
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