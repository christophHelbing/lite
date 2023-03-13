package com.sevdesk.lite.failure

import arrow.core.Either

fun<T> trap (functionBlock: () -> T): Either<Failure, T> {
    return try {
        Either.Right(functionBlock())
    } catch (e: Exception) {
        Either.Left(Failure.EncapsulationFailure(e))
    }
}