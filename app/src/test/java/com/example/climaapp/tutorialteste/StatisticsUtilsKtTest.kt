package com.example.climaapp.tutorialteste

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class StatisticsUtilsKtTest{

    @Test
    fun getActiveAndCompleteStats_noCompleted_returnsHundredZero(){
        //create an active task
        val tasks = listOf<Task>(
            Task("testes", "testes", isCompleted = false)

        )

        // call your function
        val result = getActiveAndCompletedStats(tasks)

        // check the result


        assertThat("activeTasksPercentage", result.activeTasksPercent, `is` (100f))
        assertThat("completedTasksPercentage", result.completedTasksPercent, `is` (0f))
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred(){
        val tasks = listOf(
            Task("title", "desc", isCompleted = true)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertThat("activeTasksPercent", result.activeTasksPercent, `is`(0f))
        assertThat("completedTasksPercent", result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty(){
        // Given 3 completed tasks and 2 active tasks
        val tasks = listOf(
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false)
        )
        // When the list of tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros(){
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros(){
        //When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        //Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }
}