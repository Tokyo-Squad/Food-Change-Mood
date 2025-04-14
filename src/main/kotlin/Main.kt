package org.example

import org.example.debendency.projectModule
import org.example.logic.CsvRepository
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(projectModule)
    }
         //show all the meals
        val repository:CsvRepository= getKoin().get()
        repository.getMeals().forEach { println(it) }


}