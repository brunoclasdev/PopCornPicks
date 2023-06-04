package br.com.bclas.popcornpicks.framework.connector

interface RetrofitConnector {
    fun baseUrl(baseUrl: String): RetrofitConnector
    fun <T> create(service: Class<T>): T
}