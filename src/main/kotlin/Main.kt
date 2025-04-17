package org.example

import dependencyinjection.projectModule
import org.example.dependencyinjection.useCaseModule
import org.koin.core.context.GlobalContext.startKoin

fun main() {
    startKoin {
        modules(projectModule,useCaseModule)
    }
}