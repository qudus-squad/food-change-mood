package logic

import kotlin.math.min

class SearchAlgorithm {

    fun knuthMorris(mealNameInDataSource: String, searchInput: String): Boolean {
        if (searchInput.isEmpty()) return true
        if (mealNameInDataSource.isEmpty()) return false
        val longestProperPrefix = computeProperPrefix(searchInput)
        var patternLength = 0
        var substringLength = 0
        while (patternLength < mealNameInDataSource.length) {
            if (searchInput[substringLength] == mealNameInDataSource[patternLength]) {
                patternLength++
                substringLength++
            }
            if (substringLength == searchInput.length) {
                return true
            } else if (patternLength < mealNameInDataSource.length && searchInput[substringLength] != mealNameInDataSource[patternLength]) {
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

    fun levenshteinDistance(searchInput: String, mealNameInDataSource: String): Int {
        val inputLength = searchInput.length
        val mealNameLength = mealNameInDataSource.length
        val longestProperPrefix = Array(inputLength + 1) { IntArray(mealNameLength + 1) }
        for (pattern in 0..inputLength) {
            longestProperPrefix[pattern][0] = pattern
        }
        for (pattern in 0..mealNameLength) {
            longestProperPrefix[0][pattern] = pattern
        }
        for (pattern in 1..inputLength) {
            for (suffix in 1..mealNameLength) {
                longestProperPrefix[pattern][suffix] = if (searchInput[pattern - 1] == mealNameInDataSource[suffix - 1]) {
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
        return longestProperPrefix[inputLength][mealNameLength]
    }

    fun calculateMatching(expectedDistance: Int, resultLength: Int, matchLength: Int): Int {
        val maxLength = maxOf(resultLength, matchLength)
        return if (maxLength == 0) 100 else ((maxLength - expectedDistance) * 100 / maxLength)
    }
}
