package io.snowflakegenerator

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1")
class SnowflakeController(private val generator: Generator) {
	private val logger = KotlinLogging.logger {}

	@GetMapping("/next-id")
	suspend fun generate(): Map<String, Any> {
		val id = generator.nextId()
		val podName = System.getenv("POD_NAME")

		logger.info("POD_NAME: $podName")

		return mapOf(
			"id" to id,
			"pod" to podName
		)
	}
}
