package br.com.docencia.openia.usecases

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

class ChatMessageServiceTest {

    private val mapper = ObjectMapper()
    private val logger: Logger = LoggerFactory.getLogger(ChatMessageService::class.java)

    @Value("\${open-ai.system-message}")
    private val systemMessage: String = ""

    @Test
    fun testComposeTopicContent() {
        val chatMessageService = ChatMessageService(mapper)
        chatMessageService.logger = logger

        val topic = "Sample Topic"
        val content = "Sample Content"
        val maxGrade = 10
        val expectedMessage = "  O enunciado do trabalho se apresenta como segue: \\\"Sample Topic\\\". \n" +
                "                E este é o conteúdo do trabalho: \\\"Sample Content\\\"."

        val result = chatMessageService.correctionWorkTests(topic, content, maxGrade)

        assertEquals(expectedMessage, result)
    }

    @Test
    fun testComposeText() {
        val chatMessageService = ChatMessageService(mapper)
        chatMessageService.logger = logger

        val text = "Sample Text"
        val expectedMessage = "  A pergunta do aluno: \"Sample Text\"."

        val result = chatMessageService.automaticStudentAnswer(text)

        assertEquals(expectedMessage, result)
    }

    @Test
    fun testParseContent() {
        val chatMessageService = ChatMessageService(mapper)
        chatMessageService.logger = logger

        val content = "Sample Content"
        val expectedParsedContent = "{ $content }"

        val result = chatMessageService.parseContent(expectedParsedContent)

        assertEquals(expectedParsedContent, result)
    }
}
