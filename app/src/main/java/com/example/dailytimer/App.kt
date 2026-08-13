package com.example.dailytimer

import android.app.Application
import com.example.dailytimer.data.db.AppDatabase

class App : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
}
