package data

enum class ColumnIndex(val index: Int) {
    NAME(0),
    ID(1),
    MINUTES(2),
    CONTRIBUTOR_ID(3),
    SUBMITTED(4),
    TAGS(5),
    NUTRITION(6),
    NUMBER_OF_STEPS(7),
    STEPS(8),
    DESCRIPTION(9),
    INGREDIENTS(10),
    NUMBER_OF_INGREDIENTS(11);
}

enum class NutritionEntityIndex(val index: Int) {
    CALORIES(0),
    TOTAL_FAT(1),
    SUGAR(2),
    SODIUM(3),
    PROTEIN(4),
    SATURATED_FAT(5),
    CARBOHYDRATES(6);
}