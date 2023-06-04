package br.com.bclas.popcornpicks.framework.connector

import android.content.Context
import br.com.bclas.popcornpicks.framework.util.ConnectorType
import retrofit2.Retrofit

internal class ConnectorImpl : Connector{
    private lateinit var baseUrl: String
    private lateinit var context: Context
    private lateinit var service: Any

    override fun setBaseUrl(baseUrl: String): Connector = apply {
        this.baseUrl = baseUrl
    }

    override fun setContext(context: Context): Connector = apply {
        this.context = context
    }

    override fun setTypeConnector(type: ConnectorType): Connector = apply {
        defineConnector(type)
    }

    private fun defineConnector(type: ConnectorType) : Retrofit {
        when(type){
            ConnectorType.RETROFIT -> TODO()
            ConnectorType.VOLLEY -> TODO()
        }
    }
}