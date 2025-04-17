package org.example

import dependencyinjection.projectModule
import org.koin.core.context.GlobalContext.startKoin
import org.example.dependencyinjection.useCaseModule

fun main() {
    startKoin {
        modules(projectModule,useCaseModule)
    }
}