package org.example

import dependencyinjection.projectModule
import org.koin.core.context.GlobalContext.startKoin

fun main() {
    startKoin {
        modules(projectModule)
    }
}