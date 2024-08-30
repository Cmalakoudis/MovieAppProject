// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    id ("com.android.application") version "8.2.2" apply false
//    id ("com.android.library") version "8.2.2" apply false
//    id ("org.jetbrains.kotlin.android") version "2.0.0" apply false
//    id ("dev.flutter.flutter-plugin-loader") version "1.0.0"
//    id ("com.android.application") version "7.3.1" apply false
//    id ("org.jetbrains.kotlin.android") version "2.0.0" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
}
