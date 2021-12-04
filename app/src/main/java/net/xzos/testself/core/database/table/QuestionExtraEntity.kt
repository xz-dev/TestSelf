package net.xzos.testself.core.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_extra")
data class QuestionExtraEntity(
    @ColumnInfo(name = "question_owner_id") val question_owner_id: String,
    @ColumnInfo(name = "answer_right_num") var ansRightNum: Int = 0,
    @ColumnInfo(name = "answer_incorrect_num") var ansIncorrectNum: Int = 0,
    @ColumnInfo(name = "answerable_num") var answerableNum: Int = 2,
    @ColumnInfo(name = "answer_incorrect") var answerIncorrect: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
)