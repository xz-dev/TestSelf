package net.xzos.testself.core.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.xzos.testself.core.database.table.QuestionSort


class Converters {

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return if (list.isNullOrEmpty())
            null
        else
            Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(s: String?): List<String> {
        if (s.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<ArrayList<String>?>() {}.type
        return Gson().fromJson(s, listType)
    }

    @TypeConverter
    fun fromStringSet(list: Set<String>?): String? {
        return fromStringList(list?.toList())
    }

    @TypeConverter
    fun stringToSet(s: String?): Set<String> {
        return stringToList(s).toSet()
    }

    @TypeConverter
    fun fromMapToString(dict: Map<String, String?>?): String? {
        return if (dict.isNullOrEmpty())
            null
        else
            Gson().toJson(dict)
    }

    @TypeConverter
    fun stringToMap(s: String?): MutableMap<String, String> {
        if (s.isNullOrEmpty()) return mutableMapOf()
        val type = object : TypeToken<Map<String, String>?>() {}.type
        return Gson().fromJson(s, type)
    }

    @TypeConverter
    fun fromQuestionSortToString(questionSort: QuestionSort) = questionSort.name

    @TypeConverter
    fun stringToQuestionSort(s: String): QuestionSort = QuestionSort.valueOf(s)
}