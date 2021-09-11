package io.noobymatze.kokoon

import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.handlers.BlockingHandler
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.nio.charset.StandardCharsets

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val builder = Undertow.builder().addHttpListener(8080, "localhost", BlockingHandler(
            handle<Response> {
                when (it) {
                    is Request.Hello -> Response.Hello("Hello ${it.name}")
                }
            }
        )).build()

        builder.start()
    }

    inline fun <reified R: Response> handle(crossinline f: (Request<out R>) -> R): HttpHandler = HttpHandler {
        val request = Json.decodeFromStream<Request<R>>(it.inputStream)
        val response = f(request)
        val body = Json.encodeToString(response)
        it.responseSender.send(body)
    }

}