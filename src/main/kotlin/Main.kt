package org.example

import dependencyinjection.projectModule
import org.example.dependencyinjection.useCaseModule
import org.example.presentation.ConsoleUi
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent

fun main() {
    startKoin {
        modules(projectModule, useCaseModule)
    }
    val ui: ConsoleUi = KoinJavaComponent.getKoin().get()
    ui.start()
}