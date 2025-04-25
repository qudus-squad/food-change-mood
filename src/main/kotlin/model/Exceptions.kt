package model


class InvalidCountryNameException(message: String) : Exception(message)

class NoMealsFoundException(message: String) : Exception(message)

class NoSweetsFoundException(message: String) : Exception(message)

class InvalidInputSuggestEasyMealsException(message: String) : Exception(message)

class InvalidMealNumberException(message: String) : Exception(message)

class NoSeafoodMealsFoundException(message: String) : Exception(message)

class InvalidNameMealException(message: String) : Exception(message)

class NotEnoughMealsException(message: String) : Exception(message)

class InvalidGetGymMealsException(message: String) : Exception(message)
