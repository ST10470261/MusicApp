package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess
import android.widget.ArrayAdapter

class DetailView : AppCompatActivity() {

    private lateinit var lvSongEntries: ListView
    private lateinit var tvHighestRatedSong: TextView
    private lateinit var btnBackToMain: Button
    private lateinit var btnExitApp: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_view)
        // Initialize views
        lvSongEntries = findViewById(R.id.lvSongEntries)
        tvHighestRatedSong = findViewById(R.id.tvHighestRatedSong)
        btnBackToMain = findViewById(R.id.btnBackToMain)
        btnExitApp = findViewById(R.id.btnExitApp)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, )
        lvSongEntries.adapter = adapter

        // Retrieve the GameSessionProcessor object from the Intent
        val gameSessionProcessor = intent.getParcelableExtra<SongProcessor>("gameSessionProcessor")

        if (gameSessionProcessor != null) {
            // Display all recorded entries in the ListView
            val formattedEntries = gameSessionProcessor.generateFormattedEntries()
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, formattedEntries)

            // Display calculated values
            val avgSong = String.format("%.2f", gameSessionProcessor.calculateAverageSong())
            tvHighestRatedSong.text = "Average minutes played per day: $avgSong min"
            val (highestRatedSong, highestRating) = gameSessionProcessor.findHighestRatedSong()
            tvHighestRatedSong.text = "Highest-rated song: $highestRatedSong ($highestRating)"


        } else {
            // Handle case where processor object is null (shouldn't happen with correct flow)
            tvHighestRatedSong.text = "Highest-rated song: N/A"
            lvSongEntries.adapter = null
        }


        // Set up Back to Main Screen button click listener
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Using FLAG_ACTIVITY_CLEAR_TOP and FLAG_ACTIVITY_SINGLE_TOP to ensure we return to an existing MainActivity instance
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Finish this activity to remove it from the back stack
        }

        // Set up Exit App button click listener
        btnExitApp.setOnClickListener {
            finishAffinity() // Closes all activities in the task associated with this activity
            exitProcess(0)
        }
    }
}


