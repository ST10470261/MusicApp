package com.example.musicapp

import android.os.Parcel
import android.os.Parcelable



class SongProcessor : Parcelable {
    private val MAX_ENTRIES = 4
    private val songName: Array<String?> = arrayOfNulls(MAX_ENTRIES)
    private val artistName: Array<String?> = arrayOfNulls(MAX_ENTRIES) // Consistently use String
    private val comment: Array<String?> = arrayOfNulls(MAX_ENTRIES)
    private val rating: Array<Int?> = arrayOfNulls(MAX_ENTRIES)

    var entryCount: Int = 0
        private set // Good practice to make setter private if only modified internally

    // Constructor used by Parcelable.Creator and potentially for default instantiation
    constructor()

    constructor(parcel: Parcel) : this() {
        // Read data in the order it was written
        // It's generally safer to read the count first if array sizes depend on it,
        // but for fixed-size arrays read from Parcel, it's okay.
        // However, if you were writing variable-sized lists, you'd write the size first.

        // For fixed-size arrays that might not be full, entryCount is crucial.
        // We'll assume entryCount is written last and read last as in your original code.

        parcel.readStringArray(songName)
        parcel.readStringArray(artistName) // Read as String array
        parcel.readStringArray(comment)

        // For the Int array (rating), it's a bit more involved if you want to keep it as Array<Int?>
        // Reading it directly as an IntArray and then converting might be easier if all elements are guaranteed.
        // Given it's Array<Int?>, reading it as a list of nullable Integers or handling nulls carefully is needed.
        // A simple way is to read as Serializable if the array contains nulls.
        // Or, write the size and then loop, writing each element with a flag for null.
        // For simplicity with Parcelable and fixed size nullable arrays:
        val ratingData = parcel.createIntArray() // Reads an IntArray
        ratingData?.forEachIndexed { index, value ->
            if (index < MAX_ENTRIES) {
                // This assumes 0 was written for nulls, or you have a way to denote null.
                // A common way to handle nullable primitive arrays is to write a boolean array indicating nulls separately,
                // or to write them as Objects (Integer) if efficiency is not paramount.
                // Let's assume you handle nulls by writing a special value (e.g., -1) or by ensuring non-null before write.
                // Or, more robustly for Array<Int?>:
                // parcel.readSerializable() as? Array<Int?> might be an option, but has overhead.

                // Reading an array of objects (Integer) is often cleaner for nullables:
                // val ratingObjects = parcel.readArray(Int::class.java.classLoader) as Array<Int?>
                // System.arraycopy(ratingObjects, 0, rating, 0, ratingObjects.size.coerceAtMost(MAX_ENTRIES))
                // For now, let's try reading as IntArray and hoping for the best, or adjusting write.
                // The most robust way is to iterate:
            }
        }
        // Let's refine how 'rating' is written and read.
        // If writeArray wrote it as Object[], then:
        val ratingObjects = parcel.readArray(Int::class.java.classLoader)
        if (ratingObjects != null) {
            for (i in ratingObjects.indices) {
                if (i < MAX_ENTRIES) {
                    rating[i] = ratingObjects[i] as? Int
                }
            }
        }
        entryCount = parcel.readInt()
    }

    /**
     * Adds a new song entry to the arrays.
     * @return True if entry was added successfully, false if max entries reached.
     */
    // In SongProcessor.kt
    fun addEntry(song: String, artist: String, newComment: String, newRating: Int): Boolean { // Changed to Int
        if (entryCount < MAX_ENTRIES) {
            songName[entryCount] = song
            artistName[entryCount] = artist
            comment[entryCount] = newComment
            rating[entryCount] = newRating // Assign Int directly
            entryCount++
            return true
        }
        return false
    }
    // In SongProcessor.kt
    fun addEntry(song: String, artist: String, newComment: String, newRatingStr: String): Boolean {
        if (entryCount < MAX_ENTRIES) {
            // ...
            rating[entryCount] = newRatingStr.toIntOrNull() // Convert here
            // ...
            return true
        }
        return false
    }

    /**
     * Finds the highest-rated Song and its comment (or artist if you prefer).
     * @return A Pair of (Comment/Description of Highest Rated Song, Highest Rating as String) or ("N/A", "N/A") if no entries.
     */
    fun findHighestRatedSong(): Pair<String, String> {
        if (entryCount == 0) return Pair("N/A", "N/A")

        var highestRatingValue = 0
        var associatedInfo = "N/A" // Could be comment, song name, etc.

        for (i in 0 until entryCount) {
            rating[i]?.let { currentRating ->
                if (currentRating > highestRatingValue) {
                    highestRatingValue = currentRating
                    // Decide what info to show: comment, song name, or artist
                    associatedInfo = comment[i] ?: songName[i] ?: artistName[i] ?: "N/A"
                }
            }
        }
        return Pair(associatedInfo, highestRatingValue.toString())
    }

    /**
     * Generates a formatted string for all recorded entries.
     * @return A list of strings, each representing a formatted song entry.
     */
    fun generateFormattedEntries(): List<String> {
        val formattedList = mutableListOf<String>()
        if (entryCount == 0) {
            formattedList.add("No song entries recorded yet.")
            return formattedList
        }

        for (i in 0 until entryCount) {
            val currentSongName = songName[i] ?: "N/A"
            val currentArtistName = artistName[i] ?: "N/A"
            val currentComment = comment[i] ?: "N/A"
            val currentRatingInt = rating[i]
            val currentRatingStr = currentRatingInt?.toString() ?: "N/A"

            val feedback = currentRatingInt?.let {
                when (it) {
                    1 -> "It's not a first song choice"
                    2 -> "It's not a bad song"
                    3 -> "It's alright"
                    4 -> "Now we getting there :)"
                    5 -> "Absolutely love this song!!!"
                    else -> "No specific feedback for this rating."
                }
            }

            formattedList.add(
                "Song Title: $currentSongName\n" +
                        "Artist: $currentArtistName\n" +
                        "Description: $currentComment\n" +
                        "Rating: $currentRatingStr/5 ($feedback)\n" +
                        "-----------------------------------"
            )
        }
        return formattedList
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringArray(songName)
        parcel.writeStringArray(artistName)
        parcel.writeStringArray(comment)
        // For Array<Int?>, writing as Array<Any?> (which Integer is) is common.
        parcel.writeArray(rating as Array<Any?>) // Cast to Array<Any?> for nullables
        parcel.writeInt(entryCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getMaxEntries() {
        // In SongProcessor.kt
        fun getMaxEntries(): Int { // Add return type
            return MAX_ENTRIES     // Return the constant
        }
    }

    companion object CREATOR : Parcelable.Creator<SongProcessor> {
        override fun createFromParcel(parcel: Parcel): SongProcessor {
            return SongProcessor(parcel)
        }

        override fun newArray(size: Int): Array<SongProcessor?> {
            return arrayOfNulls(size)
        }
    }
    fun calculateAverageSong(): Double {
        if (entryCount == 0) return 0.0
        var sumOfRatings = 0
        var actualRatingsCount = 0
        for (i in 0 until entryCount) {
            rating[i]?.let {
                sumOfRatings += it
                actualRatingsCount++
            }
        }
        return if (actualRatingsCount > 0) sumOfRatings.toDouble() / actualRatingsCount else 0.0
    }
}