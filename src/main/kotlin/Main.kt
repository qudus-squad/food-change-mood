/*
import data.MealCsvParser

fun main() {

*/
/*    val meal = MealCsvParser().splitCsvLineData(
        "a bit different  breakfast pizza,31490,30,26278,2002-06-17,\"['30-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'breakfast', 'main-dish', 'pork', 'american', 'oven', 'easy', 'kid-friendly', 'pizza', 'dietary', 'northeastern-united-states', 'meat', 'equipment']\",\"[173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0]\",9,\"['preheat oven to 425 degrees f', 'press dough into the bottom and sides of a 12 inch pizza pan', 'bake for 5 minutes until set but not browned', 'cut sausage into small pieces', 'whisk eggs and milk in a bowl until frothy', 'spoon sausage over baked crust and sprinkle with cheese', 'pour egg mixture slowly over sausage and cheese', 's& p to taste', 'bake 15-20 minutes or until eggs are set and crust is brown']\",this recipe calls for the crust to be prebaked a bit before adding ingredients. feel free to change sausage to ham or bacon. this warms well in the microwave for those late risers.,\"['prepared pizza crust', 'sausage patty', 'eggs', 'milk', 'salt and pepper', 'cheese']\",6 "
    )
    println(meal[11])*//*

println(filterData()[0])
}
fun filterData(): List<String> {
    val content = "arriba   baked winter squash mexican style,137739,55,47892,2005-09-16,\"['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']\",\"[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]\",11,\"['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']\",\"autumn is my favorite time of year to cook! this recipe can be prepared either spicy or sweet, your choice! two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.\",\"['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']\",7\n" +
            "a bit different breakfast pizza,31490,30,26278,2002-06-17,\"['30-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'breakfast', 'main-dish', 'pork', 'american', 'oven', 'easy', 'kid-friendly', 'pizza', 'dietary', 'northeastern-united-states', 'meat', 'equipment']\",\"[173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0]\",9,\"['preheat oven to 425 degrees f', 'press dough into the bottom and sides of a 12 inch pizza pan', 'bake for 5 minutes until set but not browned', 'cut sausage into small pieces', 'whisk eggs and milk in a bowl until frothy', 'spoon sausage over baked crust and sprinkle with cheese', 'pour egg mixture slowly over sausage and cheese', 's& p to taste', 'bake 15-20 minutes or until eggs are set and crust is brown']\",this recipe calls for the crust to be prebaked a bit before adding ingredients. feel free to change sausage to ham or bacon. this warms well in the microwave for those late risers.,\"['prepared pizza crust', 'sausage patty', 'eggs', 'milk', 'salt and pepper', 'cheese']\",6"

    val filteredData = mutableListOf<String>()
    var commaCount = 0
    var currentString = ""
    var flag = false

    content.forEachIndexed { index, char ->
        if (char == '[') {
            flag = true
        } else if (char == ']') {
            flag = false
        } else if (!flag) {
            if (commaCount == 12 && (char == ' ' || char == '\n')) {
                filteredData.add(currentString.trim())
                currentString = ""
                commaCount = 0
            } else if (char == ',') {
                commaCount++
            }
        }
        currentString += char
    }

    return filteredData
}
*/
import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import data.MealCsvParser
import model.MealItem
import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val path = Path("food (1).csv")
    val content = path.readText()
    val reader: CsvReader = csvReader()
    val rows = reader.readAllWithHeader(content)

    val meals = mutableListOf<MealItem>()
    val headers = rows.firstOrNull()?.keys?.toList() ?: emptyList()

    println("Headers: ${headers.joinToString()}")

    var count = 0
    var errorCount = 0

    for (row in rows) {
        if (row.values.all { it.isNullOrBlank() }) continue  // Skip empty lines
        count++

        val line = headers.joinToString(",") { header ->
            val field = row[header]?.trim() ?: ""
            if (field.contains(",") || field.contains("\"")) {
                "\"${field.replace("\"", "\"\"")}\""
            } else {
                field
            }
        }

        try {
            val meal = MealCsvParser().parseOneLine(line)
            meals.add(meal)
        } catch (e: Exception) {
            errorCount++
            println("Error at row $count: ${e.message}")
        }

        if (count % 100 == 0) print(".")
    }

    println("\n\nTotal rows processed: $count")
    println("Successfully parsed: ${meals.size}")
    println("Failed to parse: $errorCount")

    meals.take(3).forEachIndexed { i, meal ->
        println("\nMeal #${i + 1}: ${meal.name} - ${meal.ingredients.take(3).joinToString()}")
    }
}
