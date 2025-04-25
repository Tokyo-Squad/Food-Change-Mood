package org.example.utils

import org.example.model.Meal
import kotlin.math.abs

class MealSearchIndex(private val meals: List<Meal>) {
    private val trigramIndex = mutableMapOf<String, MutableSet<Int>>()
    private val normalizedNames = mutableMapOf<Int, String>()

    init {
        meals.forEachIndexed { index, meal ->
            val normalized = preprocessForFuzzySearch(meal.name)
            normalizedNames[index] = normalized
            addToTrigramIndex(normalized, index)
        }
    }

    /**
     * Adds all 3-character sequences (trigrams) from a string to the index
     * @param str The string to index (normalized meal name)
     * @param index The meal's position in the meals list
     */
    private fun addToTrigramIndex(str: String, index: Int) {
        for (i in 0..str.length - 3) {
            val trigram = str.substring(i, i + 3)
            trigramIndex.getOrPut(trigram) { mutableSetOf() }.add(index)
        }
    }

    /**
     * Searches for meals matching the query
     * @param query Search string
     * @param maxResults Maximum number of results to return
     * @return List of matching meals sorted by relevance
     */
    fun search(query: String, maxResults: Int = 20): List<Meal> {
        if (query == "") {
            throw MealAppException.InvalidArgumentException()
        }
        val normalizedQuery = preprocessForFuzzySearch(query)
        val candidates = findCandidateIndices(normalizedQuery)

        return candidates.asSequence()
            .distinct()
            .map { meals[it] to calculateScore(normalizedNames[it]!!, normalizedQuery) }
            .sortedByDescending { it.second }
            .take(maxResults)
            .map { it.first }
            .toList()
    }

    /**
     * Finds potential candidate meals using trigram overlap
     * @param query Normalized search query
     * @return Set of candidate meal indices
     */
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

    /**
     * Calculates a relevance score between text and query
     * Scoring rules:
     * - Exact match: 1.0 + length bonus
     * - Bitap fuzzy match: 0.8 + length bonus
     * - Partial match: 0.5 * length similarity
     */
    private fun calculateScore(text: String, query: String): Double {
        val bitapMatch = bitapFuzzySearch(text, query)
        val lengthSimilarity = 1 - abs(text.length - query.length) / 20.0
        return when {
            text.contains(query) -> 1.0 + lengthSimilarity
            bitapMatch -> 0.8 + lengthSimilarity
            else -> 0.5 * lengthSimilarity
        }
    }
}