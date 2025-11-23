# Smart Travel Planning Assistant ✈️  
_A Kotlin Jetpack Compose Travel Planner App for CP3406_

Smart Travel Planning Assistant is a mobile app built for **CP3406 – Mobile App Development**, implemented entirely with **Kotlin + Jetpack Compose + MVVM + Room + Hilt**.  
It provides a complete travel organization experience including **Trip management**, **Itinerary planning**, **Expense tracking**, and **real-world API integration**.

---

## 📱 Core Features

### 🧭 1. Trip Management  
Users can:  
- Create trips with name, destination, start/end dates  
- View all trips in a list  
- Select an active trip (affects itinerary & expenses)  
- Persist data locally via Room Database  

### 📝 2. Itinerary Planning  
Each trip contains multiple itinerary items.  
Users can:  
- Add itinerary activities (title, time, location, notes)  
- Display itinerary items for the selected trip  
- Use Compose UI for fully reactive updates  

### 💰 3. Expense Tracking  
Users can record all travel expenses:  
- Amount  
- Category (Food, Transport, Hotel, etc.)  
- Currency  
- Notes  

Additional Features:  
- Automatic category-based expense summary  
- Real-time total updates using Kotlin Flow  

### 🌍 4. Real-Time Destination Info (REST Countries API)  
The app integrates with the **REST Countries API** to automatically fetch:  
- Country name  
- Capital  
- Region  
- Population  

This enriches the Trip experience with real-world travel information.

---

## 🧰 Tech Stack

### **Frontend & UI**
- **Kotlin**
- **Jetpack Compose (Material 3)**
- **Navigation Compose**
- **State management with Flow + StateFlow**

### **Architecture**
- **MVVM architecture pattern**  
- **Repository pattern**  
- **Dependency Injection with Hilt**

### **Database**
- **Room Database**
    - Tables:
        - Trip
        - ItineraryItem
        - Expense
    - Full CRUD support
    - Reactive Flow queries

### **Networking**
- **Retrofit + Moshi**
- Base URL: `https://restcountries.com/v3.1/`
- Endpoint:  
  ```http
  GET /name/{country_name}


## 🏗 Project Structure

```text
app/
 └── src/main/java/com/cp3406/smarttravelplanningassistant
      ├── data/
      │    ├── dao/ (Room DAOs)
      │    ├── entity/ (Room entities)
      │    ├── database/ (Room database)
      ├── di/ (Hilt modules)
      ├── navigation/ (Navigation graph)
      ├── ui/
      │    ├── components/
      │    ├── screens/
      │    │    ├── trips/
      │    │    ├── itinerary/
      │    │    ├── expenses/
      │    ├── viewmodel/
      │    ├── theme/
      ├── TravelAssistantApp.kt
      └── MainActivity.kt
```



## 🗄 Database Schema

### Trip Table
| Field        | Type   |
|--------------|--------|
| id           | Int (PK) |
| name         | String |
| destination  | String |
| startDate    | String |
| endDate      | String |

### ItineraryItem Table
| Field      | Type   |
|------------|--------|
| id         | Int (PK) |
| tripId     | Int (FK) |
| title      | String |
| time       | String |
| location   | String |
| notes      | String |

### Expense Table
| Field      | Type   |
|------------|--------|
| id         | Int (PK) |
| tripId     | Int (FK) |
| amount     | Double |
| category   | String |
| currency   | String |
| notes      | String |


## 🧰 Tech Stack

- Kotlin
- Jetpack Compose (Material3)
- MVVM Architecture
- Hilt Dependency Injection
- Room Database
- Retrofit + Moshi for networking
- Kotlin Coroutines & Flow


## 🌐 API Integration

This app integrates with the REST Countries API:

GET https://restcountries.com/v3.1/name/japan

**Used for:**
- Fetching destination information
- Displaying capital, region, population, flag



## Timeline
| Week | Task |
|------|------|
| 4–5 | Database + booking features |
| 6 | Itinerary creation |
| 7 | Expense tracking + notifications |
| 8–9 | Testing and finalization |

## Author
[Tang Renxian]  
Student ID: [14889930]  
James Cook University
