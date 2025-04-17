package utils

object ListUtils {

    /**
     * Extension function for List<T> that checks if the list is empty.
     *
     * @param exception A lambda that returns the exception to be thrown if the list is empty.
     * @return The original list if it is not empty.
     * @throws Throwable The exception provided if the list is empty.
     *
     * Use this function when you want to safely ensure a list has data before proceeding.
     * It helps avoid manually writing repeated if-checks and improves readability.
     *
     * Instead of writing:
     * val topMeals = meals
     *     .filter { it.isTopRated }
     *     .takeIf { it.isNotEmpty() }
     *     ?: throw NoTopMealsFoundException("No top-rated meals available")
     *
     * You can write:
     * val topMeals = meals
     *     .filter { it.isTopRated }
     *     .orThrowIfEmpty { NoTopMealsFoundException("No top-rated meals available") }
     */
    inline fun <T> List<T>.orThrowIfEmpty(exception: () -> Throwable): List<T> {
        return takeIf { it.isNotEmpty() } ?: throw exception()
    }
}
