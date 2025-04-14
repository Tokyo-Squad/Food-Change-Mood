package org.example

import org.koin.core.context.GlobalContext.startKoin

fun main() {
    startKoin {
        modules()
    }
}