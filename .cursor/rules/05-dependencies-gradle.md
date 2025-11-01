---
description: Dependencies & Gradles 
globs:
alwaysApply: true 
---

- Use version catalog aliases (`libs.*`) for libraries and plugins; don?t hardcode versions.
- For Compose, import the BOM (`platform(libs.androidx.compose.bom)`) and avoid explicit versions for Compose artifacts.
- Don?t modify the Gradle wrapper or AGP/Kotlin versions unless specifically asked.
- Common tasks: `./gradlew assembleDebug`, `./gradlew test`, `./gradlew connectedAndroidTest`, `./gradlew lint`