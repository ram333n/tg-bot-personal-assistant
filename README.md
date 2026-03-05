# tg-bot-personal-assistant

Telegram bot — personal assistant (Bachelor diploma project).

## ✨ Features

### 🇬🇧 Text Translation
Enter any text and select a target language — the bot will translate it for you.

### 📱 QR Code Generation
Enter any text or URL and the bot will generate and send back a QR code image.

### 🗒️ Notes Management
Create, view, update, and delete personal notes.
- Notes have a **title** and **content** (up to 2000 characters).
- Browse your notes through a paginated list with navigation buttons.
- Delete notes with a confirmation step.

### 🔔 Notification Scheduling
Schedule reminders that the bot will send to you at the specified time.

Two ways to schedule:

**Manual:** Enter a description and a time in one of the supported formats:
- `yyyy-MM-dd HH:mm` — e.g. `2024-05-10 14:00`
- `HH:mm` — e.g. `01:30` (schedules for today)

**AI-assisted:** Describe your goal in natural language (e.g. *"Remind me about my machine learning lecture at 12:30"* or *"Schedule mealtime for tomorrow"*) and the bot will suggest a list of notifications for you to confirm or reject.

You can also view and delete your pending notifications.

### ⛅ Weather Forecast
Enter a city name and get the current weather forecast for that location.

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4.4 |
| Database | PostgreSQL |
| Job Scheduling | JobRunr |
| Translation | Google Cloud Translate API |
| QR Code | Google ZXing |
| Weather | OpenWeatherMap API |
| AI | Spring AI + Groq (Llama 3 70B) |
| Containerization | Docker |
| Build Tool | Maven |
