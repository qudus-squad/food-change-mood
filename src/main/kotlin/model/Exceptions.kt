package model


class InvalidCountryNameException(message: String) : Exception(message)

class NoMealsFoundException(message: String) : Exception(message)

class NoSweetsFoundException(message: String) : Exception(message)

class InvalidMealNumberException(message: String) : Exception(message)

class NoSeafoodMealsFoundException(message: String) : Exception(message)

class InvalidNameMealException(message: String) : Exception(message)

class NotEnoughMealsException(message: String) : Exception(message)

open class InvalidInputSuggestEasyMealsException(message: String) : Exception(message)

class InvalidCountOfSuggestionsInput(errorMessage: String) : InvalidInputSuggestEasyMealsException(errorMessage)

class InvalidPreparationTimeInput(errorMessage: String) : InvalidInputSuggestEasyMealsException(errorMessage)

class InvalidCountOfIngredientsInput(errorMessage: String) : InvalidInputSuggestEasyMealsException(errorMessage)

class InvalidCountOfPreparationStepsInput(errorMessage: String) : InvalidInputSuggestEasyMealsException(errorMessage)

open class InvalidGetGymMealsException(message: String) : Exception(message)

class InvalidCaloriesInput(errorMessage: String) : InvalidGetGymMealsException(errorMessage)

class InvalidProteinInput(errorMessage: String) : InvalidGetGymMealsException(errorMessage)

class InvalidNumberOfMealsInput(errorMessage: String) : InvalidGetGymMealsException(errorMessage)

class InvalidCaloriesToleranceInput(errorMessage: String) : InvalidGetGymMealsException(errorMessage)

class InvalidProteinToleranceInput(errorMessage: String) : InvalidGetGymMealsException(errorMessage)

