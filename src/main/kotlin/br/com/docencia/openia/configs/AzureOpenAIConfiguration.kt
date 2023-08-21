package br.com.docencia.openia.configs

import br.com.docencia.openia.interceptors.OpenAIInterceptor
import br.com.docencia.openia.interfaces.AzureOpenAIAPI
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class AzureOpenAIConfiguration {

    @Value("\${open-ai.base-url}")
    lateinit var baseURL : String

    @Value("\${open-ai.api-key}")
    lateinit var token : String

    @Bean
    fun azureOpenAIAPI() : AzureOpenAIAPI {
        val retrofit = this.createRetrofit()
        return retrofit.create(AzureOpenAIAPI::class.java)
    }

    fun createClient() : OkHttpClient {
        val interceptor = OpenAIInterceptor(this.token)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor).connectionPool(ConnectionPool(5, 1, TimeUnit.SECONDS))
            .readTimeout(Duration.ZERO.toMillis(), TimeUnit.MILLISECONDS)
            .build()
    }

    private fun createMapper() : ObjectMapper {

        return ObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        }
    }

    fun createRetrofit() : Retrofit {
        val client = this.createClient()

        return Retrofit.Builder().baseUrl(this.baseURL)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(this.createMapper()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}