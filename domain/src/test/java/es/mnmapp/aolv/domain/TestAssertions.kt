package es.mnmapp.aolv.domain

fun (() -> Any?).shouldNotThrowException() =
        try {
            invoke()
        } catch (ex: Exception) {
            throw Error("expected not to throw!", ex)
        }

fun (() -> Any?).shouldThrowException() = try { invoke() } catch (ex: Exception) { }