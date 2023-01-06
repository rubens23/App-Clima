package com.example.climaapp.tutorialteste



    internal fun getActiveAndCompletedStats(tasks: List<Task>?): StatsResult {

        return if (tasks == null || tasks.isEmpty()){
            StatsResult(0f, 0f)
        }else{
            val totalTasks = tasks!!.size
            val numberOfActiveTasks = tasks.count { it.isActive }
            val activePercent = 100 * numberOfActiveTasks / totalTasks
            val completePercent = 100 * (totalTasks - numberOfActiveTasks) / totalTasks

            return StatsResult(
                activeTasksPercent = activePercent.toFloat(),
                completedTasksPercent = completePercent.toFloat()
            )

        }



    }



    data class StatsResult(val activeTasksPercent: Float, val completedTasksPercent: Float)
