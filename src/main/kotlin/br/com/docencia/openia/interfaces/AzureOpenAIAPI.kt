package br.com.docencia.openia.interfaces

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatCompletionResult
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface AzureOpenAIAPI {

    @POST("/openai/deployments/{deployment_id}/chat/completions")
    fun createChatCompletion(
        @Body request : ChatCompletionRequest,
        @Path("deployment_id") deploymentId : String,
        @Query("api-version") apiVersion : String)
    : Single<ChatCompletionResult>
}