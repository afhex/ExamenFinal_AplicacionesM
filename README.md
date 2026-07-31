# 💪 FitRutina

Aplicación Android de guía de ejercicios organizada por grupo muscular.

## Descripción

FitRutina te permite explorar ejercicios organizados por grupo muscular, ver instrucciones detalladas con imágenes, guardar tus ejercicios favoritos para armar tu rutina personal, y registrar tu progreso con fotos.

## Tecnologías

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose + Material3
- **Arquitectura**: MVVM + Repository (Clean Architecture)
- **Persistencia local**: Room + DataStore
- **API REST**: Retrofit (wger.de)
- **Imágenes**: Coil
- **Hardware**: Cámara con permisos en runtime

## Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                      CAPA DE PRESENTACIÓN                   │
│                     (UI con Jetpack Compose)                │
│  HomeScreen ── ExerciseListScreen ── ExerciseDetailScreen   │
│  FavoritesScreen ────────── SettingsScreen ── AddProgress   │
└──────────────────────────────┬──────────────────────────────┘
                               │ (Observa StateFlow / Flow)
┌──────────────────────────────▼──────────────────────────────┐
│                    CAPA DE VIEWMODEL (MVVM)                 │
│         ExerciseViewModel   ───   SettingsViewModel         │
└──────────────────────────────┬──────────────────────────────┘
                               │ (Llama métodos del Repository)
┌──────────────────────────────▼──────────────────────────────┐
│                    CAPA DE REPOSITORIO                      │
│            ExerciseRepository / ExerciseRepositoryImpl       │
│             (Fuente Única de Verdad - Single Source)        │
└──────────────┬──────────────────────────────┬───────────────┘
               │                              │
┌──────────────▼──────────────┐ ┌─────────────▼──────────────┐
│     FUENTE LOCAL (Room)     │ │    FUENTE REMOTA (Retrofit) │
│  AppDatabase / DAOs         │ │  WgerApiService            │
│  (Favoritos y Fotos)        │ │  (API wger.de REST)        │
└─────────────────────────────┘ └────────────────────────────┘
               │
┌──────────────▼──────────────┐
│       DATASTORE PREFS       │
│  UserPreferencesManager     │
│  (Modo Oscuro / Peso kg-lb) │
└─────────────────────────────┘
```

## Estructura de Paquetes

```
com.fitrutina.app/
├── data/                    # Capa de Datos (Data Layer)
│   ├── local/               # Base de datos Room (Entities, DAOs, Database)
│   │   ├── dao/             # FavoriteExerciseDao, ProgressPhotoDao
│   │   ├── entity/          # FavoriteExercise, ProgressPhoto
│   │   └── AppDatabase.kt
│   ├── preferences/         # Preferencias DataStore (UserPreferencesManager)
│   ├── remote/              # API REST Retrofit (ApiService, RetrofitClient, DTOs)
│   │   ├── api/             # WgerApiService
│   │   └── dto/             # ExerciseCategoryDto, ExerciseDto
│   └── repository/          # Patrón Repositorio (ExerciseRepository, ExerciseRepositoryImpl)
└── ui/                      # Capa de Presentación (UI Layer)
    ├── common/              # Componentes reusables (UiState, NetworkImage, StateComponents)
    ├── navigation/          # Navegación con NavHost (Screen, AppNavigation)
    ├── screens/             # Pantallas en Jetpack Compose
    ├── theme/               # Sistema de diseño Material3 (Color, Theme, Type)
    └── viewmodel/           # ViewModels con StateFlow / Coroutines
```

## Pantallas

1. **Home** — Categorías musculares consumidas desde la API REST
2. **Lista de Ejercicios** — Filtrado por grupo muscular
3. **Detalle del Ejercicio** — Instrucciones completas, imágenes con Coil y botón de favorito
4. **Favoritos** — Rutina personal persistida en Room
5. **Ajustes** — Modo oscuro y unidad de peso guardadas en DataStore
6. **Agregar Progreso** — Captura de fotos con la cámara y guardado local

## API

- [wger.de API](https://wger.de/api/v2/) — Gratuita, sin API key
