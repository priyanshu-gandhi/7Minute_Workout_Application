package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>
    {
        val exerciseList =ArrayList<ExerciseModel>()

        val jumpingJacks =ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val squat =ExerciseModel(
            2,
            "Squat",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val bench_press =ExerciseModel(
            3,
            "Bench Press",
            R.drawable.ic_bench_press,
            false,
            false
        )
        exerciseList.add(bench_press)

        val plank =ExerciseModel(
            4,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val deadlift =ExerciseModel(
            5,
            "Dead Lift",
            R.drawable.ic_deadlift,
            false,
            false
        )
        exerciseList.add(deadlift)

        val yoga =ExerciseModel(
            6,
            "Yoga",
            R.drawable.ic_yoga,
            false,
            false
        )
        exerciseList.add(yoga)


        return exerciseList

    }
}