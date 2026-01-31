package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var strSongNameTxt: EditText
    private lateinit var strArtistNameTxt: EditText
    private lateinit var lblRating: EditText // Or TextView if just for display
    private lateinit var lblComment: EditText // Or TextView if just for display
    private lateinit var spinnerDescription: Spinner
    private lateinit var spinnerRating: Spinner
    private lateinit var btnAddEntry: Button
    private lateinit var btnClearInputs: Button
    private lateinit var btnGoToDetails: Button

    private lateinit var songProcessor: SongProcessor // Renamed for clarity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize SongProcessor FIRST
        songProcessor = SongProcessor()

        // Initialize views
        strSongNameTxt = findViewById(R.id.strSongNameTxt)
        strArtistNameTxt = findViewById(R.id.strArtistName)
        lblRating = findViewById(R.id.lblRating)
        lblComment = findViewById(R.id.lblComment)
        spinnerDescription = findViewById(R.id.spinnerDescription)
        spinnerRating = findViewById(R.id.spinnerRating)
        btnAddEntry = findViewById(R.id.btnAddEntry)
        btnClearInputs = findViewById(R.id.btnClearInputs)
        btnGoToDetails = findViewById(R.id.btnViewDetails)


        // Setup Spinners with data
        // Assuming you have R.array.descriptions (or similar) for the description spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.descriptions_array, // REPLACE with your actual array resource for descriptions
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDescription.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.ratings_array, // REPLACE with your actual array resource for ratings (e.g., 1, 2, 3, 4, 5)
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRating.adapter = adapter
        }

        // Set up Add Entry button click listener
        btnAddEntry.setOnClickListener {
            addSongEntry()
        }

        // Set up Clear Inputs button click listener
        btnClearInputs.setOnClickListener {
            clearInputFields()
        }

        // Set up Go to Details button click listener
        btnGoToDetails.setOnClickListener {
            val intent = Intent(this, DetailView::class.java)
            // Pass the processor object to the next activity
            // Ensure SongProcessor is Parcelable or Serializable for this to work correctly.
            intent.putExtra("songProcessor", songProcessor)
            startActivity(intent)
        }

        // Initial check for max entries when the activity is created
        updateAddEntryButtonState()
    }

    override fun onResume() {
        super.onResume()
        // Update button state when returning to this activity,
        // e.g., after coming back from DetailView
        updateAddEntryButtonState()
    }

    private fun addSongEntry() {
        val songName = strSongNameTxt.text.toString().trim()
        val artistName = strArtistNameTxt.text.toString().trim()
        val ratingStr = spinnerRating.selectedItem.toString()
        val comment =
            spinnerDescription.selectedItem.toString() // Or from lblComment.text.toString() if it's an EditText

        // --- Error Handling & Validation ---
        val songNameValidationResult = validateSongName(songName)
        if (songNameValidationResult != "Valid") {
            Toast.makeText(this, songNameValidationResult, Toast.LENGTH_SHORT).show()
            return // Stop further processing
        }

        val rating: Int
        try {
            rating = ratingStr.toInt()
            if (rating < 1 || rating > 5) { // Assuming ratings are 1-5
                Toast.makeText(this, "Rating must be between 1 and 5.", Toast.LENGTH_SHORT).show()
                return
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid rating selected.", Toast.LENGTH_SHORT).show()
            return
        }
        // --- End Error Handling ---

        // Add entry using SongProcessor
        // Pass the actual comment from the spinner or EditText, not the rating string again for the comment parameter.
        val added = songProcessor.addEntry(songName, artistName, comment, rating)

        if (added) {
            Toast.makeText(
                this,
                "Entry added successfully! Total entries: ${songProcessor.entryCount}",
                Toast.LENGTH_SHORT
            ).show()
            clearInputFields()
        } else {
            Toast.makeText(
                this,
                "Maximum ${songProcessor.getMaxEntries()} entries reached. Cannot add more.", // Assuming SongProcessor has a way to get max entries
                Toast.LENGTH_LONG
            ).show()
        }
        updateAddEntryButtonState() // Update button state after adding
    }



    }


    private fun clearInputFields() {
        strSongNameTxt.text.clear()
        strArtistNameTxt.text.clear()
        lblRating.text.clear()    // If it's an EditText for rating input
        lblComment.text.clear()   // If it's an EditText for comment input
        // Reset spinners to the first item (optional)
        if (spinnerRating.adapter != null && spinnerRating.adapter.count > 0) {
            spinnerRating.setSelection(0)
        }
        if (spinnerDescription.adapter != null && spinnerDescription.adapter.count > 0) {
            spinnerDescription.setSelection(0)
        }
    }

    private fun updateAddEntryButtonState() {
        // Check if songProcessor is initialized (it should be by this point if called after onCreate)
        if (::songProcessor.isInitialized) {
            // Assuming SongProcessor has a getMaxEntries() method or a public maxEntries property
            val maxEntries = songProcessor.getMaxEntries() // Or some constant like 7
            if (songProcessor.entryCount >= maxEntries) {
                btnAddEntry.isEnabled = false
                if (songProcessor.entryCount > 0) { // Only show toast if entries exist
                    Toast.makeText(this, "Maximum $maxEntries entries reached.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                btnAddEntry.isEnabled = true
            }
        } else {
            // This case should ideally not happen if songProcessor is initialized in onCreate.
            // You might want to disable the button by default if initialization could fail.
            btnAddEntry.isEnabled = false // Or true, depending on desired default
        }
        // --- Validation Helper Functions ---
        fun validateSongName(songName: String): String {
            if (songName.isBlank()) {
                return "Error: Song name cannot be empty."





