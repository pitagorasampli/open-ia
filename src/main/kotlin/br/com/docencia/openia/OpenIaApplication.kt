package br.com.docencia.openia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenIaApplication

fun main(args: Array<String>) {
	runApplication<OpenIaApplication>(*args)
}
