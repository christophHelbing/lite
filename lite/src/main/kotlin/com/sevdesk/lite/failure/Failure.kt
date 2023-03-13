package com.sevdesk.lite.failure

import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import kotlin.reflect.KClass

sealed class Failure(val failureMessage: String) {
    data class ValidationError(
        val validationMessage: String
    ) : Failure(failureMessage = validationMessage)

    data class NotFoundFailure(
        val id: Long,
        val entityType: KClass<*>
    ) : Failure(failureMessage = "Element with ID: $id of type: ${entityType.simpleName} was not found.")

    class EncapsulationFailure(
        exception: Exception
    ) : Failure(failureMessage = exception.localizedMessage)
}

fun<T> Failure.toResponseEntity(logger: Logger): ResponseEntity<T> {
    return when(this) {
        is Failure.NotFoundFailure -> {
            logger.warn(this.failureMessage)
            ResponseEntity.notFound().build()
        }
        is Failure.ValidationError -> {
            logger.warn(this.failureMessage)
            ResponseEntity.badRequest().build()
        }
        is Failure.EncapsulationFailure -> {
            logger.error(this.failureMessage)
            ResponseEntity.internalServerError().build()
        }
    }
}