package io.snowflakegenerator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1")
class SnowflakeController(private val generator: Generator) {
	@GetMapping("/next-id")
	suspend fun generate(): Map<String, Any> {
		val id = generator.nextId()

		println("POD_NAME: ${System.getenv("POD_NAME")}")

		return mapOf(
			"id" to id,
			"pod" to System.getenv("POD_NAME")
		)
	}
}
