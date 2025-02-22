package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding : ActivityHistoryBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.historyToolbar)
        supportActionBar?.title="History"

        if(supportActionBar !=null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.historyToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao=(application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao)

    {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{ allCompleteDatesList->
                if(allCompleteDatesList.isNotEmpty())
                {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility =View.VISIBLE
                    binding?.tvNodataAvailable?.visibility =View.INVISIBLE

                    binding?.rvHistory?.layoutManager =LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for(date in allCompleteDatesList)
                    {
                        dates.add(date.date)
                    }

                    val historyAdapter =HistoryAdapter(dates)
                    binding?.rvHistory?.adapter =historyAdapter



                }
                else
                {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility =View.GONE
                    binding?.tvNodataAvailable?.visibility =View.INVISIBLE

                }

            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding =null
    }
}