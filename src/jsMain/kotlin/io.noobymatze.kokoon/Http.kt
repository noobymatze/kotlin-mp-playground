package io.noobymatze.kokoon

import kotlinx.browser.window
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

val json = Json { ignoreUnknownKeys = true }

sealed class Error {

    object NetworkError: Error()

}


inline fun <reified R: Response> send(url: String, request: Request<R>): Promise<Result<Error, R>> =
    Promise<Result<Error, R>> { resolve, reject ->
        val xhr = XMLHttpRequest()
        xhr.open("POST", url)
        xhr.onload = { event ->
            val statusCode = xhr.status

            resolve(Result.Success(json.decodeFromString<R>(xhr.responseText)))
        }
        xhr.onerror = { event ->
            resolve(Result.Failure(Error.NetworkError))
        }

        xhr.send(Json.encodeToString(request))
    }