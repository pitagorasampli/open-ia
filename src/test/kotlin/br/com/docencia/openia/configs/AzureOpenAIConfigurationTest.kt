package br.com.docencia.openia.configs

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AzureOpenAIConfigurationTest {

    @Test
    fun testCreateRetrofit() {
        val configuration = AzureOpenAIConfiguration()
        configuration.baseURL = "https://sofia-tutoria.openai.azure.com/"
        configuration.token = "d4ec36834dcc44b180cad5cfd2a25508"

        val retrofit = configuration.createRetrofit()

        // Verifica se o retrofit foi criado corretamente
        assert(retrofit.baseUrl().toString() == "https://sofia-tutoria.openai.azure.com/")
    }

    @Test
    fun testCreateClient() {
        val configuration = AzureOpenAIConfiguration()
        configuration.token = "d4ec36834dcc44b180cad5cfd2a25508"

        val client = configuration.createClient()

        // Verifica se o client foi criado corretamente
        assert(client.readTimeoutMillis.toLong() == 0L)
    }

}
