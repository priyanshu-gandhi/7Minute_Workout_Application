package com.example.a7minutesworkout

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding


import com.example.a7minutesworkout.databinding.ActivityMainBinding
import com.example.a7minutesworkout.databinding.CustomDialogBoxBinding
import com.example.a7minutesworkout.ui.theme.ExerciseStatusAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExerciseBinding? = null
    private var tts : TextToSpeech? = null

    private var restTimer : CountDownTimer?= null
    private var restProgress =0

    private var exerciseTimer : CountDownTimer? =null
    private var exerciseProgress =0

    private var exerciseList : ArrayList<ExerciseModel>?= null
    private var currentExercisePosition =-1

    private var exerciseAdapter : ExerciseStatusAdapter?= null

    private var player : MediaPlayer?=null

    fun createNotificationChannel(context : Context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "first_notificaton"
            val channelName = "Awareness"
            val channelDescription = "My first Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            //creating notification in this function

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.large_icon)
                .setContentTitle("Are you afraid of Workout!!")
                .setContentText("Leave the procastination, and work hard in gym because only you can do it for yourself")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)


            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1
                    )
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notify(1, builder.build())
            }

        }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar !=null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList =Constants.defaultExerciseList()

        tts= TextToSpeech(this, this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customdialogboxshow()
            //onBackPressedDispatcher.onBackPressed()

            createNotificationChannel(this@ExerciseActivity)


        }


        setupRestView()
        setupExerciseStatusRecyclerView()


        }

        private fun customdialogboxshow()
        {
            val custom_dialog= Dialog(this)
            val dialog_binding = CustomDialogBoxBinding.inflate(layoutInflater)
            custom_dialog.setContentView(dialog_binding.root)
            custom_dialog.setCanceledOnTouchOutside(false)

            dialog_binding.btnYes.setOnClickListener {
                finish()
                custom_dialog.dismiss()
            }

            dialog_binding.btnNo.setOnClickListener {
                custom_dialog.dismiss()
            }

           custom_dialog.show()

        }

    override fun onBackPressed() {
        customdialogboxshow()
        //super.onBackPressed()
    }



    private fun  setupExerciseStatusRecyclerView()
    {
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter=ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setupExerciseView()
    {

        speakout(exerciseList!![currentExercisePosition].getname())

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.upcomingText?.visibility =View.INVISIBLE
        binding?.upcomingExercise?.visibility =View.INVISIBLE
        binding?.tvExercise?.visibility =View.VISIBLE
        binding?.tvExerciseView?.visibility =View.VISIBLE
        binding?.ivImage?.visibility =View.VISIBLE



            binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getimage())
            binding?.tvExercise?.text = exerciseList!![currentExercisePosition].getname()

        if(exerciseTimer!=null)
        {
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        setExerciseProgressbar()


    }





    private fun setupRestView()
    {

        try {
            player=MediaPlayer.create(this,R.raw.sound_file)
            player?.start()


        }catch (e : Exception)
        {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.upcomingText?.visibility =View.VISIBLE
        binding?.upcomingExercise?.visibility =View.VISIBLE
        binding?.tvExercise?.visibility =View.INVISIBLE
        binding?.tvExerciseView?.visibility =View.INVISIBLE
        binding?.ivImage?.visibility =View.INVISIBLE


        binding?.upcomingExercise?.text=exerciseList!![currentExercisePosition+1].getname()

        if(restTimer !=null)
        {
            restTimer?.cancel()
            restProgress=0
        }
        speakout(binding?.upcomingText!!.text.toString())
        speakout(binding?.upcomingExercise!!.text.toString())
        setRestProgressbar()
    }

       private fun setRestProgressbar()
       {
           binding?.ProgressBar?.progress =restProgress

           restTimer =object: CountDownTimer(5000,1000)
           {
               override fun onTick(millisUntilFinished: Long) {
                  restProgress++
                   binding?.ProgressBar?.progress =5-restProgress
                   binding?.tvtimer?.text=(5-restProgress).toString()
               }

               override fun onFinish() {
                   currentExercisePosition++

                   exerciseList!![currentExercisePosition].setisSelected(true)
                   exerciseAdapter!!.notifyDataSetChanged()

                   Log.d("rest",""+"${currentExercisePosition}")
                   if(currentExercisePosition <= exerciseList?.size!! -1)
                   {
                       setupExerciseView()
                   }
                   else
                   {
                       setupRestView()
                   }


               }

           }.start()
       }



    private fun setExerciseProgressbar()
    {
        binding?.ProgressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(5000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.ProgressBarExercise?.progress=5-exerciseProgress
                binding?.tvtimerExercise?.text= (5-exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercisePosition].setisCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                Log.d("rest_exe",""+"${currentExercisePosition}")
                if(currentExercisePosition < exerciseList?.size!!-1)
                {
                    setupRestView()
                }
                else if(currentExercisePosition == exerciseList?.size!! -1)
                {
                    Log.d("rest_int",""+"${currentExercisePosition}")
                    val intent= Intent(this@ExerciseActivity, Finish_Activity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

        }.start()

    }




    override fun onDestroy() {
        super.onDestroy()

        if(restTimer !=null)
        {
            restTimer?.cancel()
            restProgress=0
        }

        if (tts!=null)
        {
            tts?.stop()
            tts?.shutdown()
        }

        if(player !=null)
        {
            player?.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS)
        {
            val result= tts!!.setLanguage(Locale("hi","IN"))

            if(result== TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("lang","This is not supported")
            }


        }
    }

    private fun speakout(text :String)
    {
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }


}