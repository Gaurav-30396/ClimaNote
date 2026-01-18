# ClimaNote

ClimaNote is a minimalist Android weather application focused on clarity over clutter.  
Instead of overwhelming users with raw metrics, it converts real-time weather data into a single meaningful summary and a practical action, helping users understand their day at a glance.

---

## Features

- One-line weather summary in plain language  
- One-line actionable suggestion (umbrella, hydration, outdoor advice)  
- Real-time weather data using the OpenWeather API  
- Remembers the last selected city across app restarts  
- Clean handling of loading and error states  
- Minimal UI focused on usability  

---

## Architecture and Tech Stack

Built using modern Android development best practices:

- Jetpack Compose for declarative UI  
- MVVM architecture for clear separation of concerns  
- Retrofit for network requests  
- Kotlin Coroutines and StateFlow for asynchronous state management  
- DataStore for persistent user preferences  
- Lifecycle-aware ViewModel for reliable UI state handling  

---

## App Flow

1. User selects a city  
2. App fetches live weather data  
3. Weather is interpreted into:
   - a short, meaningful summary  
   - a practical, actionable suggestion  
4. The selected city is saved and restored automatically on the next launch  

Weather data is always fetched fresh.  
Only the user’s city preference is persisted.

---

## Design Philosophy

Clarity over clutter. Meaning over metrics.

ClimaNote intentionally avoids information overload and focuses on delivering what matters most, quickly and clearly.

---
<img width="540" height="1204" alt="image" src="https://github.com/user-attachments/assets/b48cfc68-8abd-4a6e-8618-c20ba184e092" />

<img width="540" height="1204" alt="image" src="https://github.com/user-attachments/assets/74b2c6f8-7ef2-4d7d-8bb1-c23510e8f968" />

<img width="540" height="1204" alt="image" src="https://github.com/user-attachments/assets/06c4aaa7-789d-42bf-82b2-62cb6b6c088c" />


---


## Getting Started

1. Clone the repository  
2. Open the project in Android Studio  
3. Add your OpenWeather API key  
4. Run the app on an emulator or physical device  

---



## Notes

- Weather data depends on API availability and location accuracy  
- New API keys may take some time to activate  
