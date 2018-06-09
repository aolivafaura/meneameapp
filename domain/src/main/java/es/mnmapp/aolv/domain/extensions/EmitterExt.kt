package es.mnmapp.aolv.domain.extensions

import io.reactivex.FlowableEmitter

fun <T> FlowableEmitter<T>.emitNext(value: T) {
    if (!isCancelled) {
        onNext(value)
    }
}

fun <T> FlowableEmitter<T>.emitError(error: Throwable) {
    if (!isCancelled) {
        tryOnError(error)
    }
}