package com.grvexample.data.model

data class MultipleChoiceQuestion (
    val question: String,
    val optionList: List<Answer>
)