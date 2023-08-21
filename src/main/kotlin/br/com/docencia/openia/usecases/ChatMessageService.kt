package br.com.docencia.openia.usecases

import com.fasterxml.jackson.databind.ObjectMapper
import com.theokanning.openai.completion.chat.ChatCompletionResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ChatMessageService(private val mapper : ObjectMapper) {

    var logger: Logger = LoggerFactory.getLogger(ChatMessageService::class.java)

    @Value("\${open-ai.system-message-correction}")
    private var systemMessageCorrection : String? = " "

    @Value("\${open-ai.system-message-answer}")
    private var systemMessageAnswerStudent : String? = " "

    fun correctionWorkTests(topic: String?, content: String?, maxGrade: Int) : String {

        val msgContent = """O enunciado do trabalho se apresenta como segue: \"$topic\". 
                E este é o conteúdo do trabalho: \"$content\"."""
        logger.info("ChatMessageService - correcao trabalho/provas - Message: $msgContent")

        return "${String.format(this.systemMessageCorrection!!, maxGrade)} $msgContent"
    }

    fun automaticStudentAnswer(text: String?) : String {

        val msgContent = "A pergunta do aluno: \"$text\"."

        logger.info("ChatMessageService - pergunta aluno - Message: $msgContent")

        return "${String.format(this.systemMessageAnswerStudent!!)} $msgContent"
    }

    fun <T> parse(chatResult : ChatCompletionResult, clazz : Class<T>) : T {
        val parsedContent = this.parseContent(chatResult.choices.first().message.content)
        return mapper.readValue(parsedContent, clazz)
    }

    fun parseContent(content : String) : String {
        val start = content.indexOf('{')
        val end = content.lastIndexOf('}')
        return content.substring(start, end + 1)
    }
}