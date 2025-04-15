package org.example

import dependencyinjection.projectModule
import org.example.dependencyinjection.useCaseModule
import org.example.logic.CsvRepository
import org.koin.core.context.GlobalContext.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(projectModule,useCaseModule)
    }
    val repository: CsvRepository = getKoin().get()
    repository.getMeals().forEach { println(it) }
}