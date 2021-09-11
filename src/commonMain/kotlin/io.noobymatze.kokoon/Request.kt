package io.noobymatze.kokoon

import kotlinx.serialization.Serializable

@Serializable
sealed class Request<R: Response> {

    @Serializable
    data class Hello(val name: String): Request<Response.Hello>()

}