package com.grvexample.ui

import androidx.lifecycle.ViewModel
import com.grvexample.R
import com.grvexample.adapter.OnItemActionListener
import com.grvexample.adapter.ViewType
import com.grvexample.data.model.Answer
import com.grvexample.data.model.MultipleChoiceQuestion
import com.grvexample.data.model.SingleChoiceQuestion
import com.grvexample.livedata.SingleLiveEvent
import com.grvexample.utils.ResourceProvider

class MainVM(private val resourceProvider: ResourceProvider) : ViewModel(),
    OnSingleChoiceClickListener<SingleChoiceQuestion>, OnMultipleChoiceClickListener<Answer> {

    private val list = ArrayList<ViewType<*>>()

    val insertEvent = SingleLiveEvent<Pair<Int, ViewType<*>>>()

    val removeItemEvent = SingleLiveEvent<Int>()

    val updateEvent = SingleLiveEvent<Pair<Int, ViewType<*>>>()

    fun getList(): List<ViewType<*>> {
        list.clear()
        val singleChoiceQuestion = SingleChoiceQuestion(
            question = resourceProvider.getString(R.string.txt_question_car),
            optionOne = resourceProvider.getString(R.string.txt_yes),
            optionTwo = resourceProvider.getString(R.string.txt_no)
        )
        list.add(SingleChoiceViewType(singleChoiceQuestion))

        val multipleChoiceQuestion = MultipleChoiceQuestion(
            question = resourceProvider.getString(R.string.txt_multiple_choice_question),
            optionList = arrayListOf(
                Answer(resourceProvider.getString(R.string.txt_reverse_camera)),
                Answer(resourceProvider.getString(R.string.txt_parking_sensor)),
                Answer(resourceProvider.getString(R.string.txt_infotainment_system))
            )
        )
        list.add(QuestionViewType(multipleChoiceQuestion.question))

        multipleChoiceQuestion.optionList.forEach {
            list.add(MultipleAnswerViewType(it))
        }

        return list
    }

    override fun onSingleChoiceOptionClicked(optionId: Int, item: SingleChoiceQuestion) {
        when {
            optionId == 1 && item.isOptionOneSelected == false -> {
                item.isOptionOneSelected = true
                list.add(1, CarDescriptionViewType())
                insertEvent.value = 1 to list[1]
            }
            optionId == 2 && item.isOptionOneSelected == true -> {
                item.isOptionOneSelected = false
                list.removeAt(1)
                removeItemEvent.value = 1
            }
        }
    }

    override fun onMultipleChoiceClicked(optionId: Int, item: Answer) {
        val index = list.indexOf(MultipleAnswerViewType(item))
        list[index] = MultipleAnswerViewType(Answer(item.answer, !item.isSelected))
        updateEvent.value = index to list[index]
    }

    override fun onItemClicked(position: Int) {
    }
}

interface OnSingleChoiceClickListener<T> : OnItemActionListener {
    fun onSingleChoiceOptionClicked(optionId: Int, item: T)
}

interface OnMultipleChoiceClickListener<T> : OnItemActionListener {
    fun onMultipleChoiceClicked(optionId: Int, item: T)
}