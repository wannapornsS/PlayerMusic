package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.player_layout.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var forwardbtn: ImageButton? = null
    private  var backwardbtn:ImageButton? = null
    private  var pausebtn:ImageButton? = null
//    private  var playbtn:ImageButton? = null
    private var mPlayer: MediaPlayer? = null
    private var songName: TextView? = null
    private  var startTime:TextView? = null
    private  var songTime:TextView? = null
    private var songPrgs: SeekBar? = null
    private var oTime = 0
    private  var sTime:kotlin.Int = 0
    private  var eTime:kotlin.Int = 0
    private  var fTime:kotlin.Int = 5000
    private  var bTime:kotlin.Int = 5000
    private val hdlr: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_layout)

        backwardbtn = findViewById<View>(R.id.btnBackward) as ImageButton
        forwardbtn = findViewById<View>(R.id.btnForward) as ImageButton
//        playbtn = findViewById<View>(R.id.btnPlay) as ImageButton
        pausebtn = findViewById<View>(R.id.btnPause) as ImageButton
        songName = findViewById<View>(R.id.txtSname) as TextView
        startTime = findViewById<View>(R.id.txtStartTime) as TextView
        songTime = findViewById<View>(R.id.txtSongTime) as TextView
        songName!!.text = "Baitikochi Chuste"
        mPlayer = MediaPlayer.create(this, R.raw.music)
        songPrgs = findViewById<View>(R.id.sBar) as SeekBar
        songPrgs!!.isClickable = false
        pausebtn!!.isEnabled = false

        btnPlay.setOnClickListener {
            Toast.makeText(this@MainActivity, "Playing Audio", Toast.LENGTH_SHORT).show()
            mPlayer?.start()
            eTime = mPlayer!!.duration
            sTime = mPlayer!!.currentPosition
            if (oTime == 0) {
                songPrgs!!.max = eTime
                oTime = 1
            }
            songTime!!.text = String.format(
                "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(eTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(eTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        eTime.toLong()
                    )
                )
            )
            startTime!!.text = String.format(
                "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        sTime.toLong()
                    )
                )
            )
            songPrgs!!.progress = sTime
            hdlr.postDelayed(UpdateSongTime, 100)
            pausebtn!!.isEnabled = true
            btnPlay!!.isEnabled = false
        }
        pausebtn!!.setOnClickListener {
            mPlayer!!.pause()
            pausebtn!!.isEnabled = false
            btnPlay!!.isEnabled = true
            Toast.makeText(applicationContext, "Pausing Audio", Toast.LENGTH_SHORT).show()
        }
        forwardbtn!!.setOnClickListener {
            if (sTime + fTime <= eTime) {
                sTime += fTime
                mPlayer!!.seekTo(sTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!btnPlay!!.isEnabled) {
                btnPlay!!.isEnabled = true
            }
        }
        backwardbtn!!.setOnClickListener {
            if (sTime - bTime > 0) {
                sTime -= bTime
                mPlayer!!.seekTo(sTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!btnPlay!!.isEnabled) {
                btnPlay!!.isEnabled = true
            }
        }


    }

    private val UpdateSongTime: Runnable = object : Runnable {
        override fun run() {
            sTime = mPlayer!!.currentPosition
            startTime!!.text = String.format(
                "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(sTime.toLong())
                )
            )
            songPrgs!!.progress = sTime
            hdlr.postDelayed(this, 100)
        }
    }


}