package net.xzos.testself.core.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "pool_owner_id") val pool_owner_id: Long,
    @ColumnInfo(name = "stem") val stem: String,
    @ColumnInfo(name = "sort") val sort: QuestionSort,
    @ColumnInfo(name = "option") val option: Map<String, String>,
    @ColumnInfo(name = "answer") val answer: List<String>,
    @ColumnInfo(name = "explain") val explain: String,
)

enum class QuestionSort {
    MCQ,
    BFQ, // 填空
    SHORT_ANS, // 简答题
}