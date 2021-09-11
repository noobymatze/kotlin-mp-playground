package io.noobymatze.kokoon

import kotlinx.serialization.Serializable

@Serializable
sealed class Response {

    @Serializable
    data class Hello(val text: String): Response()

}