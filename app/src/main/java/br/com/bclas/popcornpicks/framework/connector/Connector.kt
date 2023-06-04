package br.com.bclas.popcornpicks.framework.connector

import android.content.Context
import br.com.bclas.popcornpicks.framework.util.ConnectorType

internal interface Connector {
    fun setBaseUrl(baseUrl: String): Connector
    fun setContext(context: Context) : Connector
    fun setTypeConnector(type : ConnectorType) : Connector
}