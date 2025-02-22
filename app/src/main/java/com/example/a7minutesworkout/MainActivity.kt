package com.example.a7minutesworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {


private var binding: ActivityMainBinding?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(binding?.root)

        //val flStartButton :FrameLayout =findViewById(R.id.fl_layout)
        binding?.flLayout?.setOnClickListener{

            val intent=Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }


        binding?.flBmi?.setOnClickListener {

            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)

        }

        binding?.history?.setOnClickListener {

            val intent= Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        binding =null
    }
}

