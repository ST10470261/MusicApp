# Music Playlist App 🎵
## Developer Details:
* Name & Surname: Nhlanhla Cindi
* Student Number: ST10470261
* Group: 01
* GitHub Repository: <https://github.com/ST10470261/MusicApp.git>

## About the Project:
The Music Playlist app is an Android-based tool that allows users to catalog and manage their favourite music. The app features a high-contrast black theme with seamless functionality for tracking song titles, artists, playback time, and genres.

## Purpose:
The purpose of this app is to provide a structured way to store song details. It allows users to:
	* Input and store up to 7 unique song entries.
	• Rate songs on a 5-star scale.
	• Categorize music using a specialized genre dropdown (Spinner).
	• View a detailed summary of their playlist and total listening time.

## App Structure:
The app is built using a three-screen architecture to ensure a clean user experience:
	1. Splash Screen: Features a 5-second automatic transition, a Start button, and an Exit button.
	2. Main Entry Screen: Contains the input form for Song Name, Artist, Minutes, Genre (Dropdown), and Rating.
	3. Detailed View Screen: Displays the list of songs in a scrollable format. It includes management tools like Clear All Saved Data and Exit Application.


## How to Use the App:
1. Launch: Open the app; the Splash Screen will display for 5 seconds before moving to the Main Screen.
2. Add Entry
3. Enter the Song Name and Artist.
4. Select the Genre from the dropdown menu.
5. Set the Rating (1-5 stars).
6. Tap Add Entry (The app will block entries after the 7th song).
7. View List: Tap View Details to see your full playlist.
8. Manage Data: Tap Delete Saved Data in the Detail View to wipe the list and reset your 7 slots.
9. Exit: Use the Exit Application button on either the Splash or Detail screen to close the app completely.

## Screenshots & Error Handling:
### 1. Splash Screen
<img width="1918" height="1012" alt="splash_screen" src="https://github.com/user-attachments/assets/0325e8bc-6c61-4936-b208-1d2d897bd372" />
   
### 2. Intro
<img width="1912" height="1016" alt="intro_screen" src="https://github.com/user-attachments/assets/316dcf01-14f5-4a51-83a7-bf464745b4a4" />

### 3. Main Screen
<img width="1915" height="1017" alt="main_screen" src="https://github.com/user-attachments/assets/a2a0b534-f54d-495c-bd9d-94b0e1c2172a" />

### 4. Detail View Screen
<img width="1910" height="1016" alt="detail_view_screen" src="https://github.com/user-attachments/assets/03315861-80e0-4ba5-a207-f8114ab12f9c" />

### 5. Add Entry
<img width="1902" height="1012" alt="add_entry" src="https://github.com/user-attachments/assets/067aa748-21b6-4c86-ab3c-b428af22d2ca" />
    
### 6. First Toast
<img width="1902" height="1008" alt="first_toast" src="https://github.com/user-attachments/assets/06bd68cf-870b-4f44-8cc4-51eef1abd295" />
    
### 7. Second Toast
<img width="1908" height="1008" alt="second_toast" src="https://github.com/user-attachments/assets/c49268d9-65cd-44df-9852-669de8d4632b" />

### 8. Detail View Screen (Results)
<img width="1881" height="1013" alt="detail_view_screen1" src="https://github.com/user-attachments/assets/99e4ff52-09e9-475b-9498-e3af08fa2ec1" />
   
### 9. Deleted Data
<img width="1903" height="1007" alt="deleted_data" src="https://github.com/user-attachments/assets/ad41a895-edbc-4993-a53f-023b6d2a8da1" />
    
## Screen Previews:
* Main Screen: Displays the 48dp touch-optimized input fields.
* Playlist Screen: Shows the scrollable list of entries.
* Detailed View: Shows the summary with emojis and formatted text.
  
## Error Handling Logic
* Data Validation: Displays a Toast message if the user leaves the Song or Artist fields blank.
* Entry Restriction: Prevents the user from adding more than 7 songs to manage memory and storage.
* Accessibility: All buttons and inputs are sized to 48dp to prevent "Touch Target" errors.

## Design Considerations:
* UI/UX: Utilizes a sleek black background with white text for high readability.
* Navigation: Buttons are stacked vertically in a LinearLayout for thumb-friendly navigation.
* Feedback: Toast messages provide real-time confirmation for every action (Add, Clear, Delete).

