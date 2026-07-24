# 💪 FitRutina

Aplicación Android de guía de ejercicios organizada por grupo muscular.

## Descripción

FitRutina te permite explorar ejercicios organizados por grupo muscular, ver instrucciones detalladas con imágenes, guardar tus ejercicios favoritos para armar tu rutina personal, y registrar tu progreso con fotos.

## Tecnologías

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose + Material3
- **Arquitectura**: MVVM + Repository
- **Persistencia local**: Room + DataStore
- **API REST**: Retrofit (wger.de)
- **Imágenes**: Coil
- **Hardware**: Cámara con permisos en runtime

## Arquitectura

```
UI (Compose) → ViewModel (StateFlow) → Repository → Room / Retrofit
                                                   → DataStore
```

## Pantallas

1. Home — Categorías musculares
2. Lista de ejercicios por categoría
3. Detalle del ejercicio
4. Favoritos (rutina personal)
5. Ajustes (modo oscuro, unidad de peso)
6. Agregar progreso (cámara)

## API

- [wger.de API](https://wger.de/api/v2/) — Gratuita, sin API key
