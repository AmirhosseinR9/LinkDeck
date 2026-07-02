[README.md](https://github.com/user-attachments/files/28869522/README.md)[Uploa# LinkDeck 🔗

A modern Android app for saving, organizing, and managing web links — built entirely with Kotlin, Jetpack Compose, and clean MVVM architecture.

---

## ✨ Features

- **Save any link** — automatically fetches title, description, and cover image via web scraping (Jsoup)
- **Group organization** — create groups and filter links by category
- **Custom notes** — attach personal notes to any saved link, expandable inline on the card
- **Offline image caching** — Coil-powered image loading with persistent disk cache
- **RTL / LTR support** — instant layout switching between English (LTR) and Persian / فارسی (RTL)
- **Clean dark UI** — minimal card-based design with a deep ink-blue palette

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository Pattern |
| Database | Room (with KSP) |
| DI | Hilt |
| Image Loading | Coil |
| Web Scraping | Jsoup |
| Async | Kotlin Coroutines + Flow |

---

## 📁 Project Structure

```
app/src/main/java/com/smartlinksaver/
│
├── data/
│   ├── local/
│   │   ├── entity/         # LinkItem, Group (Room entities)
│   │   ├── dao/            # LinkItemDao, GroupDao
│   │   └── AppDatabase.kt
│   ├── model/              # WebMetadata
│   └── repository/         # LinkRepository (data + scraping)
│
├── di/
│   └── AppModule.kt        # Hilt providers
│
├── presentation/
│   └── viewmodel/
│       ├── LinkViewModel.kt
│       └── LinkUiState.kt
│
├── ui/
│   ├── theme/              # Color, Type, Theme
│   ├── components/         # LinkCard, GroupFilterRow, Dialogs, ShimmerBox
│   └── screen/             # MainScreen
│
├── LinkDeckApp.kt           # Application class (Hilt + Coil singleton)
└── MainActivity.kt
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 11+
- Android SDK 26+

### Setup

1. Clone or generate the project:
   ```bash
   python3 create_linkdeck.py
   ```

2. Open the `LinkDeck/` folder in Android Studio.

3. Add Gradle wrapper binaries (copy from any existing Android project or run):
   ```bash
   gradle wrapper
   ```

4. Sync Gradle and build.

### Build APK

**Debug:**
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

**Release (signed):**
```
Build → Generate Signed Bundle / APK → APK
```

**Install via ADB:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🗄 Database Schema

**`link_items`**
| Column | Type | Notes |
|---|---|---|
| id | INTEGER | Primary key, auto-generated |
| url | TEXT | Required |
| title | TEXT | Scraped from og:title |
| webDescription | TEXT | Scraped from og:description |
| imageUrl | TEXT | Scraped from og:image |
| userNotes | TEXT | User-written |
| groupId | INTEGER | FK → groups.id (SET NULL on delete) |

**`groups`**
| Column | Type | Notes |
|---|---|---|
| id | INTEGER | Primary key, auto-generated |
| groupName | TEXT | Required |

---

## 📦 Key Dependencies

```kotlin
// Compose BOM
androidx.compose:compose-bom:2024.09.00

// Room
androidx.room:room-runtime:2.6.1

// Hilt
com.google.dagger:hilt-android:2.51.1

// Coil
io.coil-kt:coil-compose:2.6.0

// Jsoup
org.jsoup:jsoup:1.17.2
```

---

## 🌐 RTL Support

The app supports instant RTL/LTR switching via the **FA / EN** toggle in the top bar. Layout direction is managed through `CompositionLocalProvider(LocalLayoutDirection)`, adapting all Compose layouts — rows, padding, alignment — without any structural changes.

---

## 📄 License

```
MIT License — free to use, modify, and distribute.
```
ding README.md…]()
