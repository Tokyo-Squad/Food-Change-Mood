package org.example

import org.example.debendency.projectModule
import org.koin.core.context.GlobalContext.startKoin

fun main() {
    startKoin {
        modules(projectModule)
    }
}