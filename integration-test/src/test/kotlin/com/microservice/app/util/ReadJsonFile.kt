package com.microservice.app.util

object ReadJsonFile {
    fun getContent(file: String): String {
        return this::class.java.classLoader.getResource(file).readText()
    }
}
