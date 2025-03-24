package com.qw.framework.app

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import java.io.BufferedReader
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.math.max
import kotlin.math.min

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun name() {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        val handler = CoroutineExceptionHandler { context, exception ->
            println("CoroutineExceptionHandler ${exception.message}")
            scope.launch {
                println("afterCoroutineExceptionHandler")
            }
        }
        runBlocking {
            scope.launch(handler) {
                throw IllegalArgumentException("IllegalArgumentException")
            }
            delay(2000)
        }
    }

    @Test
    fun test2() {
        val list = arrayListOf(1, 2, 3, 4)
        list.subList(0, min(list.size, 5)).forEach {
            println(it)
        }
    }

    fun disableSSLCertificateValidation() {
        try {
            // 创建一个信任所有证书的 TrustManager

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testHttps() {
        // 禁用 SSL 验证
//        disableSSLCertificateValidation()
        // 测试 HTTPS 请求
        val url = "https://124.237.214.18:10443"
        try {
            val connection = (java.net.URL(url).openConnection() as HttpsURLConnection)
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
                }
            )

            // 安装全局的 TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            connection.sslSocketFactory = sslContext.socketFactory
            connection.setHostnameVerifier { _, _ -> true }
            connection.connectTimeout = 300
            connection.readTimeout = 300
            connection.requestMethod = "GET"
            connection.connect()
            println("Response Code: ${connection.responseCode}")
            println("Response Message: ${connection.responseMessage}")
            val content = connection.inputStream.bufferedReader().use(BufferedReader::readText)
            println("Response content: $content")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun isDetectRequired(internalCount: Int, externalCount: Int): Boolean {
        val min = min(internalCount, externalCount)
        val max = max(internalCount, externalCount)
        //case 1 max > 5
        //case 2 min > 0
        return max > 5 || min > 0
    }

    @Test
    fun testDetectRequired() {
        val _00 = isDetectRequired(0, 0)
        val _01 = isDetectRequired(0, 1)
        val _05 = isDetectRequired(0, 5)
        val _06 = isDetectRequired(0, 6)
        val _11 = isDetectRequired(1, 1)
        require(!_00)
        require(!_01)
        require(!_05)
        require(_06)
        require(_11)
    }
}