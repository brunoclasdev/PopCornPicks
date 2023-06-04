package br.com.bclas.popcornpicks.framework.connector

import android.content.Context
import android.util.Log
import br.com.bclas.popcornpicks.R
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import java.util.concurrent.TimeUnit

class RetrofitConnectorImpl constructor(private val context: Context) : RetrofitConnector {

    private lateinit var baseUrl: String
    private val httpLoggingInterceptor = HttpLoggingInterceptor { Log.i("", it) }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClientDefault: OkHttpClient = generateSecureOkHttpClient()

    private fun generateSecureOkHttpClient(): OkHttpClient {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val sslContext = SSLContext.getInstance("TLSv1.2")

        keyStore.load(null, null)
        val certFactory = CertificateFactory.getInstance("X.509")

        val inputStream = context.resources.openRawResource(R.raw.themoviedb)
        val certificate = certFactory.generateCertificate(inputStream)

        keyStore.setCertificateEntry("themoviedb", certificate)

        trustManagerFactory.init(keyStore)
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        if (trustManagerFactory.trustManagers.size != 1 || trustManagerFactory.trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers: ${trustManagerFactory.trustManagers.contentToString()}")
        }

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .sslSocketFactory(
                sslContext.socketFactory,
                trustManagerFactory.trustManagers[0] as X509TrustManager
            )
            .build()

    }

    private val converterFactoryDefault: GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())

    override fun baseUrl(url: String): RetrofitConnector = apply {
        this.baseUrl = url
    }

    override fun <T> create(service: Class<T>): T {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientDefault)
            .addConverterFactory(converterFactoryDefault)
            .build()
            .create(service)
    }
}