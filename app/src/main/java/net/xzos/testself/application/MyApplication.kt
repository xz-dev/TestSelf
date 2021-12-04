package net.xzos.testself.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import net.xzos.testself.core.database.initDatabase
import org.jetbrains.annotations.Contract

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initDatabase(applicationContext)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @get:Contract(pure = true)
        lateinit var context: Context
            private set
    }
}