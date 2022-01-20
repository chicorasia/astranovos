package br.com.chicorialabs.astranovos

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.File

/**
 * Um método de conveniência copiado de https://github.com/hoang06kx1
 */
//fun MockWebServer.enqueueFromFile(fileName: String, headers: Map<String, String> = emptyMap()) {
//    val inputStream = javaClass.classLoader
//        .getResourceAsStream(fileName)
//    val source = inputStream.source().buffer()
//    val mockResponse = MockResponse()
//    for ((key, value) in headers) {
//        mockResponse.addHeader(key, value)
//    }
//    enqueue(
//        mockResponse.setBody(source.readString(Charsets.UTF_8))
//    )
//}

fun readFileDirectlyAsText(fileName: String) : String = File(fileName).readText(Charsets.UTF_8)

fun MockWebServer.enqueueFromFile(fileName: String, headers: Map<String, String> = emptyMap()) {
    val responseBody = readFileDirectlyAsText(fileName)
    val mockResponse = MockResponse()
    for ((key, value) in headers) {
        mockResponse.addHeader(key, value)
    }
    enqueue(
        mockResponse.setBody(responseBody)
    )
}