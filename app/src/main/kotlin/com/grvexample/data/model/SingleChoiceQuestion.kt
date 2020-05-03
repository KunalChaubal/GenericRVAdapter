package com.grvexample.data.model

data class SingleChoiceQuestion (
    val question: String,
    val optionOne: String,
    val optionTwo: String,
    var isOptionOneSelected: Boolean? = false
)