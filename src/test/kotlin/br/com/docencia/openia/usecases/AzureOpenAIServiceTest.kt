package br.com.docencia.openia.usecases

import br.com.docencia.openia.interfaces.AzureOpenAIAPI
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import io.reactivex.Single
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
class AzureOpenAIServiceTest {

    private val mockApi: AzureOpenAIAPI = mock()
    private val azureOpenAIService = AzureOpenAIService(mockApi)

    @Test
    fun `test retrieveChatCompletionResult`() {
        val apiVersion = "1.0"
        val deployment = "test-deployment"
        val model = "test-model"
        val temperature = 0.8
        val messageList = listOf("Hello", "How are you?")

        val request = ChatCompletionRequest.builder()
            .messages(messageList.map { ChatMessage(ChatMessageRole.SYSTEM.value(), it) })
            .model(model)
            .temperature(temperature)
            .build()

        val expectedResult = ChatCompletionResult()

        whenever(mockApi.createChatCompletion(request, deployment, apiVersion))
            .thenReturn(Single.just(expectedResult))

        val result = azureOpenAIService.retrieveChatCompletionResult(
            apiVersion, deployment, model, temperature, messageList
        )

        verify(mockApi).createChatCompletion(request, deployment, apiVersion)
        assert(result == expectedResult)
    }

    @Test
    fun testRetrieveChatCompletionResult() {
        // Mocking the dependencies
        val api = mock<AzureOpenAIAPI>()
        val service = AzureOpenAIService(api)

        // Mock data
        val apiVersion = "1.0"
        val deployment = "test-deployment"
        val model = "test-model"
        val temperature = 0.8
        val messageList = listOf("Hello", "How are you?")
        val expectedResult = ChatCompletionResult()

        // Mock the API response
        whenever(api.createChatCompletion(any(), any(), any())).thenReturn(Single.just(expectedResult))

        // Call the method to be tested
        val result = service.retrieveChatCompletionResult(apiVersion, deployment, model, temperature, messageList)

        // Verify that the API method was called with the correct parameters
        verify(api).createChatCompletion(any(), eq(deployment), eq(apiVersion))

        // Assert the result
        assertEquals(expectedResult, result)
    }
}
