package di


import data.CsvFileDataSource
import data.CsvFileReader
import data.MealsCsvParser
import logic.FoodChangeModeDataSource
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single {
        File("food.csv")
    }
    single { CsvFileReader(get()) }
    single {
        MealsCsvParser()
    }
    single<FoodChangeModeDataSource> { CsvFileDataSource(get(), get()) }
}