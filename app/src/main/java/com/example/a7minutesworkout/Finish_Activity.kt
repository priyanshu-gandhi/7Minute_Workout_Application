package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Finish_Activity : AppCompatActivity() {

    private var binding : ActivityFinishBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.finishToolbar)

        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.finishToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }


        binding?.finishBtn?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    val dao = (application as WorkoutApp).db.historyDao()
    addDatetoDatabase(dao)





    }
      private fun addDatetoDatabase (historyDao: HistoryDao)
      {

          val c =Calendar.getInstance()
          val dateTime =c.time
          Log.e("Date",""+dateTime)

          val sdf =SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())
          val date =sdf.format(dateTime)
          Log.e("Formatted Date",""+date)

          lifecycleScope.launch {
              historyDao.insert(HistoryEntity(date))
              Log.e(
                  "Date: ",
                  "Added..."
              )

          }
      }

}