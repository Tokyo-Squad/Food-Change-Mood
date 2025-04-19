package org.example.presentation.io

interface ConsoleIO {
    fun readInput(prompt: String): String?
    fun printOutput(message: String)
}