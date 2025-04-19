package org.example.presentation.io

class RealConsoleIO : ConsoleIO {
    override fun readInput(prompt: String): String? {
        print(prompt)
        return readLine()
    }

    override fun printOutput(message: String) {
        println(message)
    }
}