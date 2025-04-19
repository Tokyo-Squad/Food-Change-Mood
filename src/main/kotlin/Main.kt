package org.example

import dependencyinjection.projectModule
import org.example.dependencyinjection.uiModule
import org.example.dependencyinjection.useCaseModule
import org.example.presentation.FoodApplicationUI
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent

fun main() {
    startKoin {
        modules(projectModule, useCaseModule,uiModule)
    }
    val ui: FoodApplicationUI = KoinJavaComponent.getKoin().get()
    ui.start()
}