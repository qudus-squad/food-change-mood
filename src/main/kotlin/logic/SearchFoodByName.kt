package logic

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.min


class FoodSearchAlgorithm() {

    private val file = File("food.csv")

    fun searchFoodFromCsv(searchInput: String): List<Pair<String, String>> {
        val bestMatches = mutableListOf<Pair<String, String>>()
        val maxDistance = 3
        val minimumSimilarity = 60
        BufferedReader(FileReader(file)).use { reader ->
            reader.readLine()

            reader.readLines().forEach { line ->
                val foodList = line!!.split(",")
                val foodName = foodList.getOrNull(0)
                val id = foodList.getOrNull(1)
                if (id != null && foodName != null) {
                    if (knuthMorris(foodName, searchInput)) {
                        bestMatches.add(Pair(id, foodName))
                    } else {
                        val distance = levenshteinDistance(foodName.lowercase(), searchInput.lowercase())
                        val similarity = calculateMatching(distance, foodName.length, searchInput.length)
                        if (distance <= maxDistance && similarity >= minimumSimilarity) {
                            bestMatches.add(Pair(id, foodName))
                        }
                    }
                }
            }
        }
        return bestMatches
    }

    private fun knuthMorris(csvFile: String, searchInput: String): Boolean {
        if (searchInput.isEmpty()) return true
        if (csvFile.isEmpty()) return false
        val longestProperPrefix = computeProperPrefix(searchInput)
        var patternLength = 0
        var substringLength = 0
        while (patternLength < csvFile.length) {
            if (searchInput[substringLength] == csvFile[patternLength]) {
                patternLength++
                substringLength++
            }
            if (substringLength == searchInput.length) {
                return true
            } else if (patternLength < csvFile.length && searchInput[substringLength] != csvFile[patternLength]) {
                if (substringLength != 0) {
                    substringLength = longestProperPrefix[substringLength - 1]
                } else {
                    patternLength++
                }
            }
        }
        return false
    }

    private fun computeProperPrefix(searchInput: String): IntArray {
        val inputLength = searchInput.length
        val longestProperPrefix = IntArray(inputLength)
        var patternLength = 0
        var substrinlength = 1
        while (substrinlength < inputLength) {
            if (searchInput[substrinlength] == searchInput[patternLength]) {
                patternLength++
                longestProperPrefix[substrinlength] = patternLength
                substrinlength++
            } else {
                if (patternLength != 0) {
                    patternLength = longestProperPrefix[patternLength - 1]
                } else {
                    longestProperPrefix[substrinlength] = 0
                    substrinlength++
                }
            }
        }
        return longestProperPrefix
    }

    private fun levenshteinDistance(searchInput: String, wordInCsvFile: String): Int {
        val inputLength = searchInput.length
        val wordLength = wordInCsvFile.length
        val longestProperPrefix = Array(inputLength + 1) { IntArray(wordLength + 1) }
        for (pattern in 0..inputLength) {
            longestProperPrefix[pattern][0] = pattern
        }
        for (pattern in 0..wordLength) {
            longestProperPrefix[0][pattern] = pattern
        }
        for (pattern in 1..inputLength) {
            for (suffix in 1..wordLength) {
                longestProperPrefix[pattern][suffix] = if (searchInput[pattern - 1] == wordInCsvFile[suffix - 1]) {
                    longestProperPrefix[pattern - 1][suffix - 1]
                } else {
                    1 + min(
                        min(
                            longestProperPrefix[pattern - 1][suffix],
                            longestProperPrefix[pattern][suffix - 1]
                        ),
                        longestProperPrefix[pattern - 1][suffix - 1]
                    )
                }
            }
        }
        return longestProperPrefix[inputLength][wordLength]
    }

    private fun calculateMatching(expectedDistance: Int, resultLength: Int, matchLength: Int): Int {
        val maxLength = maxOf(resultLength, matchLength)
        return if (maxLength == 0) 100 else ((maxLength - expectedDistance) * 100 / maxLength)
    }
}