package org.example.utils

import org.example.model.Meal

class MealSearchIndex(private val meals: List<Meal>) {
    private val trigramIndex = mutableMapOf<String, MutableSet<Int>>()
    private val normalizedNames = mutableMapOf<Int, String>()

    init {
        meals.forEachIndexed { index, meal ->
            val normalized = FuzzySearch.preprocessString(meal.name)
            normalizedNames[index] = normalized
            addToTrigramIndex(normalized, index)
        }
    }

    private fun addToTrigramIndex(str: String, index: Int) {
        for (i in 0..str.length - 3) {
            val trigram = str.substring(i, i + 3)
            trigramIndex.getOrPut(trigram) { mutableSetOf() }.add(index)
        }
    }

    fun search(query: String, maxResults: Int = 20): List<Meal> {
        val normalizedQuery = FuzzySearch.preprocessString(query)
        val candidates = findCandidateIndices(normalizedQuery)

        return candidates.asSequence()
            .distinct()
            .map { meals[it] to calculateScore(normalizedNames[it]!!, normalizedQuery) }
            .sortedByDescending { it.second }
            .take(maxResults)
            .map { it.first }
            .toList()
    }

    private fun findCandidateIndices(query: String): Set<Int> {
        return if (query.length >= 3) {
            (0..query.length - 3)
                .map { query.substring(it, it + 3) }
                .flatMap { trigramIndex[it] ?: emptySet() }
                .toSet()
        } else {
            meals.indices.toSet()
        }
    }

    private fun calculateScore(text: String, query: String): Double {
        val bitapMatch = FuzzySearch.bitapSearch(text, query)
        val lengthSimilarity = 1 - Math.abs(text.length - query.length) / 20.0
        return when {
            text.contains(query) -> 1.0 + lengthSimilarity
            bitapMatch -> 0.8 + lengthSimilarity
            else -> 0.5 * lengthSimilarity
        }
    }
}