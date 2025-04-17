package data

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

class FileReader(private val filePath: String) {
    fun readContent(): String = Path(filePath).readText()
    fun readLines(): List<String> = Path(filePath).readLines()
}