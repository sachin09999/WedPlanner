Wedding Planner App
This is a modern Android application designed to help users plan their wedding by providing tools for vendor discovery, checklist management, and venue selection. The app features a clean, intuitive user interface built with Jetpack Compose.

✨ Features
User Authentication: Seamless login and registration with Firebase to securely manage user accounts.

Dynamic Navigation: A custom, animated bottom navigation bar that allows users to easily switch between the main sections of the app. The bar is intelligently hidden on screens like login and registration.

Interactive Dashboard: A home screen that greets the user and provides quick access to key features. It includes a search bar, a categorized list of vendors (e.g., Venues, Makeup, Decor), and a slideshow for promotional banners.

Vendor & Venue Discovery: Browse a curated list of popular vendors and venues. Users can view all venues and see detailed information for each one.

Wedding Checklist: A dedicated screen to help users keep track of their wedding planning tasks.

Responsive UI: The UI is designed to be visually appealing and responsive, with a smooth scrolling experience where the header remains in place while content scrolls beneath it.

💻 Tech Stack
Framework: Android with Jetpack Compose

Language: Kotlin

Architecture: MVVM (Model-View-ViewModel) with StateFlow for state management

Navigation: Jetpack Compose Navigation

Backend: Firebase Authentication

Image Loading: Coil

UI Components: compose-animated-navigationbar for the custom bottom bar animation

🚀 Getting Started
To get a copy of the project up and running on your local machine, follow these steps:

Clone the Repository:

Bash

git clone [[your-repository-url](https://github.com/sachin09999/WedPlanner.git)]
Open in Android Studio:
Open the project in Android Studio.

Set up Firebase:
Add a Firebase project and connect it to your Android app to use Firebase Authentication.

Add Dependencies:
Ensure you have the following dependency in your module-level build.gradle.kts file for the animated navigation bar:

Kotlin
implementation("com.canopas.compose-animated-navigationbar:bottombar:1.0.1")
Run the App:
Build and run the app on an emulator or a physical device.

🎨 UI Design
The app's design is characterized by a soft, elegant color palette with a clean layout. The home screen features a stylized, wavy pink background that creates a pleasing visual effect as content scrolls behind it. The main navigation icons are also animated, providing a polished and modern user experience.

🤝 Contribution
Contributions are welcome! If you have any suggestions or find a bug, please feel free to open an issue or submit a pull request.
