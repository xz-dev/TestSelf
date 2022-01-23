package net.xzos.testself.core.parser

import net.xzos.testself.core.database.table.PoolEntity
import net.xzos.testself.core.database.table.QuestionEntity
import net.xzos.testself.core.database.table.QuestionSort
import net.xzos.testself.core.manager.PoolManager
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream


const val TAG = "XlsParser"

val mcqUpperAnswerKeywords: List<String> by lazy {
    arrayListOf("A", "B", "C", "D", "E", "F", "G")
}
val mcqLowerAnswerKeywords: List<String> by lazy {
    mcqUpperAnswerKeywords.map { it.lowercase() }
}
val mcqOptionUpperKeywords: List<String> by lazy {
    mcqUpperAnswerKeywords.map { "$it." }
}

val mcqOptionLowerKeywords: List<String> by lazy {
    mcqOptionUpperKeywords.map { it.lowercase() }
}

fun init() {
    System.setProperty(
        "org.apache.poi.javax.xml.stream.XMLInputFactory",
        "com.fasterxml.aalto.stax.InputFactoryImpl"
    )
    System.setProperty(
        "org.apache.poi.javax.xml.stream.XMLOutputFactory",
        "com.fasterxml.aalto.stax.OutputFactoryImpl"
    )
    System.setProperty(
        "org.apache.poi.javax.xml.stream.XMLEventFactory",
        "com.fasterxml.aalto.stax.EventFactoryImpl"
    )
}

suspend fun xlsSaveDB(xlsInputStream: InputStream) {
    init()
    xlsToJsonList(xlsInputStream).forEach { (name, xlsList) ->
        val pool = PoolManager.savePool(name)
        xlsList.forEachIndexed { index, xlsLineMap ->
            val question = xlsLineToDB(pool, xlsLineMap, index)
            PoolManager.saveQuestion(question)
        }
    }
}

fun xlsLineToDB(
    pool: PoolEntity,
    dict: Map<String, String>,
    index: Int
): QuestionEntity {
    val optionMap = dict["OptionList"]?.let { getOptionMap(it) } ?: emptyMap()
    val sort = if (optionMap.isNotEmpty())
        QuestionSort.MCQ
    else getOtherSort(dict)
    return QuestionEntity(
        "${pool.id}-$index",
        pool.id,
        dict["Topic"] ?: "No Stem",
        sort,
        optionMap,
        getAnswerList(dict["Result"], dict["Explain"], sort).toSet(),
        dict["Explain"] ?: ""
    )
}

fun getOptionMap(optionString: String): Map<String, String> {
    if ((mcqOptionUpperKeywords + mcqOptionLowerKeywords).any { optionString.contains(it) }) {
        val neoMCQKeywords = if (mcqOptionUpperKeywords.any { optionString.contains(it) })
            mcqOptionUpperKeywords.filter {
                optionString.contains(it)
            }
        else mcqOptionLowerKeywords.filter { optionString.contains(it) }
        val mcqOptionMap = mutableMapOf<String, String>()
        for (i in neoMCQKeywords.indices) {
            val startKey = neoMCQKeywords[i]
            val endKey = if (i < neoMCQKeywords.size - 1)
                neoMCQKeywords[i + 1]
            else null
            val rawStartIndex = optionString.indexOf(startKey)
            val endIndex = endKey?.let { optionString.indexOf(it) } ?: optionString.length
            if (rawStartIndex != -1 && endIndex != -1) {
                val startIndex = rawStartIndex + startKey.length
                mcqOptionMap[startKey.substring(0, startKey.length - 1)] =
                    cleanOptionText(optionString.substring(startIndex, endIndex))
            }
        }
        return mcqOptionMap
    }
    return emptyMap()
}

fun cleanOptionText(s: String): String = s
    .replace(";;", "")
    .replace(" +", " ")

fun getAnswerList(
    resultString: String?,
    explainString: String?,
    questionSort: QuestionSort
): List<String> {
    return when (questionSort) {
        QuestionSort.MCQ -> {
            if (resultString != null) {
                mcqUpperAnswerKeywords.filter { resultString.contains(it) }.let { list ->
                    if (list.isNotEmpty()) list
                    else mcqLowerAnswerKeywords.filter { resultString.contains(it) }
                }
            } else listOf()
        }
        QuestionSort.BFQ -> explainString?.lines() ?: emptyList()
        QuestionSort.SHORT_ANS -> explainString?.let { listOf(it) } ?: emptyList()
    }
}

fun getOtherSort(dict: Map<String, String>): QuestionSort {
    return if (dict["OptionList"]?.contains("\n") == true)
        QuestionSort.SHORT_ANS
    else QuestionSort.BFQ
}

fun xlsToJsonList(xlsInputStream: InputStream): Map<String, List<Map<String, String>>> {
    val workbook = XSSFWorkbook(xlsInputStream)
    val sheet = try {
        workbook.getSheetAt(0)
    } catch (e: IllegalArgumentException) {
        return emptyMap()
    }
    val name = sheet.sheetName
    val rowNum = sheet.physicalNumberOfRows
    if (rowNum <= 1) return emptyMap()
    val argList = getArgList(sheet)
    val xlsList = mutableListOf<Map<String, String>>()
    for (ri in 1 until rowNum) {
        val dict = mutableMapOf<String, String>()
        sheet.getRow(ri)?.forEachIndexed { index, cell ->
            dict[argList[index]] = formatter.formatCellValue(cell)
        }
        if (dict.isNotEmpty())
            xlsList.add(dict)
    }
    return mapOf(name to xlsList)
}

fun getXlsSize(sheet: Sheet): Pair<Int, Int>? {
    val x = sheet.physicalNumberOfRows
    if (x <= 0) return null
    val row = sheet.getRow(0)
    val y = row.physicalNumberOfCells
    if (y <= 0) return null
    return Pair(x, y)
}

fun getArgList(sheet: Sheet): List<String> {
    return mutableListOf<String>().apply {
        sheet.getRow(0).forEach { cell ->
            add(getCellString(cell))
        }
    }
}

val formatter by lazy { DataFormatter() }

fun getCellString(cell: Cell): String = formatter.formatCellValue(cell)