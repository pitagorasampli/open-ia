package br.com.docencia.openia.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class OpenAIInterceptor(private val token : String) : Interceptor {

    private val contentType = "Content-Type"
    private val contentTypeApplicationJson = "application/json"

    private val cacheControl = "Cache-Control"
    private val cacheControlNoCache = "no-cache"

    private val apiKey = "api-key"

    override fun intercept(chain: Interceptor.Chain) : Response {
        val original = chain.request()

        val request = original.newBuilder()
            .header(contentType, contentTypeApplicationJson)
            .header(cacheControl, cacheControlNoCache)
            .header(apiKey, this.token)
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}