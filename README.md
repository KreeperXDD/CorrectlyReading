## 📋 О проекте
WeReadCorrectly — это Android-приложение, которое помогает пользователям сохранять правильную осанку во время чтения. Используя камеру устройства, приложение в реальном времени анализирует положение головы и расстояние до экрана, предупреждая пользователя о нарушениях с помощью звуковых и вибрационных сигналов.
## 🎯 Основные возможности
- Мониторинг осанки — анализ расстояния до экрана и угла наклона головы
- Настраиваемые параметры — регулировка чувствительности датчиков
- Многоуровневые оповещения — звук, вибрация или оба типа сигналов
- Выбор камеры — поддержка фронтальной и тыловой камер
- Онбординг — знакомство с функционалом при первом запуске
## 🏗 Архитектура проекта
```
CorrectlyReading/
├── .idea/                      # Настройки проекта Android Studio
│   ├── inspectionProfiles/     # Профили инспекций кода
│   ├── .name                   # Имя проекта в IDE
│   ├── AndroidProjectSystem.xml # Настройки системы сборки
│   ├── compiler.xml             # Конфигурация компилятора (JDK 21)
│   ├── deploymentTargetSelector.xml # Настройки выбора устройства
│   ├── deviceManager.xml        # Настройки менеджера устройств
│   ├── gradle.xml               # Настройки Gradle-интеграции
│   ├── misc.xml                 # Разные настройки (JDK, тип проекта)
│   ├── runConfigurations.xml    # Настройки конфигураций запуска
│   └── vcs.xml                  # Настройки Git
│
├── app/                         # Главный модуль приложения
│   ├── src/
│   │   ├── androidTest/         # Инструментальные тесты
│   │   ├── test/                # Локальные unit-тесты
│   │   └── main/
│   │       ├── java/.../        # Исходный код
│   │       └── AndroidManifest.xml
│   ├── .gitignore               # Игнорируемые файлы модуля
│   ├── build.gradle.kts         # Сборочный файл модуля
│   └── proguard-rules.pro       # Правила обфускации
│
├── gradle/                      # Конфигурация Gradle
│   ├── gradle-daemon-jvm.properties # Настройки JVM для демона
│   └── libs.versions.toml       # Каталог версий зависимостей
│
├── .gitignore                   # Игнорируемые файлы проекта
├── build.gradle.kts             # Корневой сборочный файл
├── gradle.properties             # Глобальные настройки Gradle
├── gradlew                      # Wrapper для Unix-систем
├── gradlew.bat                   # Wrapper для Windows
└── settings.gradle.kts           # Настройки многомодульного проекта
```
## 🛠 Технологический стек
### Языки и платформа
- Kotlin — основной язык разработки
- Android SDK — минимальная версия API 24 (Android 7.0), целевая API 36
### UI и навигация
- Jetpack Compose — современный UI toolkit
- Material Design 3 — готовая библиотека компонентов
- Android Navigation — навигация между экранами
- BottomNavigationView — нижняя навигационная панель
- ViewPager2 — для экранов онбординга
### Работа с камерой
- **CameraX** — библиотека для работы с камерой
  - `camera-core` — основной функционал
  - `camera-camera2` — реализация на Camera2 API
  - `camera-lifecycle` — привязка к жизненному циклу
  - `camera-view` — PreviewView для отображения
### Архитектурные компоненты
- **ViewModel** — управление состоянием UI
- **LiveData / StateFlow** — реактивное обновление данных
- **Lifecycle** — компоненты, чувствительные к жизненному циклу
### Сборка и зависимости
- **Gradle Kotlin DSL** — сборочные скрипты на Kotlin
- **Version Catalog** — централизованное управление версиями (`libs.versions.toml`)
- **Gradle Wrapper** — воспроизводимые сборки (версия Gradle 9.1.0)
- **JDK Toolchain** — автоматическая загрузка JDK 21 через foojay.io
### Тестирование
- **JUnit 4** — модульное тестирование
- **Espresso** — UI-тестирование
- **Compose UI Testing** — тестирование Compose-компонентов
## 📦 Структура кода приложения

### Пакет `data` — слой данных
```
com.example.wereadcorrently.data/
├── Settings.kt                  # Модель данных настроек
│   └── Свойства: minDistance, maxAngle, alertEnable,
│       soundEnabled, vibrationEnabled, cameraId
│
└── SettingsManager.kt           # Менеджер настроек (заглушка)
    ├── saveSettings()           # Сохранение настроек (пусто)
    ├── setOnboardingCompleted() # Отметка о прохождении онбординга (TODO)
    └── isFirstLaunch()          # Проверка первого запуска (всегда true)
```

### Пакет `service` — сервисные классы
```
com.example.wereadcorrently.service/
└── CameraHelper.kt              # Хелпер для работы с камерой (заглушка)
    └── startPreview()           # Запуск превью камеры
```
### Активности
```
com.example.wereadcorrently/
├── MainActivity.kt              # Главная активность
│   ├── Проверка первого запуска
│   ├── Запуск онбординга при необходимости
│   └── Настройка нижней навигации
│
└── ui/OnboardingActivity.kt
```
## 🔧 Конфигурационные файлы
### Корневые файлы
- .gitignore - Игнорируемые файлы (build, локальные настройки, временные файлы)
- build.gradle.kts - Плагины для всех модулей (с apply false)
- gradle.properties - JVM-аргументы (-Xmx2048m), кодировка UTF-8, стиль Kotlin
- settings.gradle.kts - Репозитории, настройка toolchains, имя проекта, модули
- gradlew / gradlew.bat - Wrapper-скрипты для Unix/Windows
### Директория `.idea/`
- .name - Имя проекта в IDE ("WeReadCorrently")
- AndroidProjectSystem.xml - Указывает на использование Gradle
- compiler.xml - Целевой уровень байт-кода — JDK 21
- deploymentTargetSelector.xml - Настройки выбора устройства для запуска
- deviceManager.xml - Сортировка устройств по имени
- gradle.xml - Модули проекта и настройки тестов
- misc.xml - JDK, тип проекта (Android), масштаб предпросмотра
- runConfigurations.xml - Отключённые продюсеры конфигураций тестов
- vcs.xml - Привязка корня проекта к Git
### Директория `gradle/`
- gradle-daemon-jvm.properties - JDK 21 для демона Gradle, URL для разных платформ
- libs.versions.toml - Каталог версий — единый источник всех зависимостей
### Файлы модуля `app/`
- .gitignore - Игнорирование /build в модуле
- build.gradle.kts - Плагины, Android-конфигурация, зависимости
- proguard-rules.pro - Пользовательские правила R8/ProGuard (пустой)
## 🚀 Сборка и запуск
### Требования
- Android Studio: Ladybug
- JDK: 21
- Android SDK: API 36+
- Устройство: Android 7.0 (API 24) или выше с камерой
### Быстрый старт
# Клонирование репозитория
git clone https://github.com/username/WeReadCorrectly.git
cd WeReadCorrectly

# Сборка проекта (Linux/macOS)
./gradlew build

# Сборка проекта (Windows)
gradlew.bat build

# Установка на устройство
./gradlew installDebug

# Запуск тестов
- ./gradlew test                    # Unit-тесты
- ./gradlew connectedAndroidTest    # Инструментальные тесты
## 📝 Статус разработки
### ✅ Реализовано
- Базовая структура многомодульного проекта
- Настройка Gradle с Version Catalog
- Конфигурация JDK toolchain (Java 21)
- Модель данных настроек (Settings)
- Главная активность с навигацией
- Проверка первого запуска
- Разрешение на использование камеры в манифесте
