package net.xzos.testself.ui.view.question

import net.xzos.testself.core.database.table.QuestionEntity

data class QuestionData(
    val question: QuestionEntity,
    var answerList: Set<String> = setOf(),
)