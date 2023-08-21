package br.com.docencia.openia.usecases

import br.com.docencia.openia.interfaces.AzureOpenAIAPI
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import io.reactivex.Single
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AzureOpenAIService(private val api: AzureOpenAIAPI) {

    @Value("\${open-ai.api-version}")
    private var defAPIVersion: String? = ""

    @Value("\${open-ai.deployment-name}")
    private var defDeployment: String? = ""

    @Value("\${open-ai.model}")
    private var defModel: String? = ""

    @Value("\${open-ai.temperature}")
    val defTemperature: Double = 1.0

    fun createChatCompletion(
        apiVersion: String, deployment: String,
        request: ChatCompletionRequest
    ): ChatCompletionResult {

        return this.execute(this.api.createChatCompletion(request, deployment, apiVersion))
    }

    fun createChatCompletionRequest(
        messageList: List<String>, model: String,
        temperature: Double
    ): ChatCompletionRequest {

        val chatMessageList = messageList.map {
            ChatMessage(ChatMessageRole.SYSTEM.value(), it)

        }.toList()

        return ChatCompletionRequest.builder()
            .messages(chatMessageList)
            .model(model)
            .temperature(temperature)
            .build()
    }

    private fun <T> execute(apiCall: Single<T>): T {
        return apiCall.blockingGet()
    }

    fun retrieveChatCompletionResult(
        apiVersion: String? = this.defAPIVersion, deployment: String? = this.defDeployment,
        model: String? = this.defModel, temperature: Double = this.defTemperature,
        messageList: List<String>
    )
            : ChatCompletionResult {
        val req = this.createChatCompletionRequest(messageList, model!!, temperature)
        return this.createChatCompletion(apiVersion!!, deployment!!, req)
    }
}