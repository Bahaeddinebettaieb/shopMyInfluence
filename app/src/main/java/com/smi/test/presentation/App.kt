package com.smi.test.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Typeface
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.FirebaseApp
import com.smi.test.presentation.utils.LanguageSettings
import io.paperdb.Paper
import java.util.*


@SuppressLint("Registered")
class App : Application(), LifecycleObserver {

    private val TAG = "App"

    companion object {
        lateinit var geoCoder: Geocoder
        lateinit var futuraCondensedBold: Typeface
        lateinit var microsoftYiBaiti: Typeface
        lateinit var context: Context
    }

    val CHANNEL_ID = "takafolworker"

    override fun onCreate() {
        super.onCreate()
        geoCoder = Geocoder(this, Locale.getDefault())
        Paper.init(this)
        initLApplicationLanguage()
        context = applicationContext
        createNotificationChannel()
        FirebaseApp.initializeApp(context);
//        initTypefaces()
    }

    private fun initLApplicationLanguage() {
//        if (Paper.book().contains(Const.APP_LANGUAGE)) {
//            val language = Paper.book().read(Const.APP_LANGUAGE) as String
//            Lingver.init(this, language)
//        } else {
//            Lingver.init(this, Locale.getDefault().toLanguageTag().toString().take(2))
//
//        }
       LanguageSettings.setLanguage(this,"en")
    }

    fun getGecoder(): Geocoder {
        return geoCoder
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Gustruck",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager: NotificationManager = getSystemService<NotificationManager>(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }

    private fun initTypefaces() {
        futuraCondensedBold = Typeface.createFromAsset(assets, "font/font1.ttf")
        microsoftYiBaiti = Typeface.createFromAsset(assets, "font/font2.ttf")
    }

    fun isActivityVisible(): String {
        return ProcessLifecycleOwner.get().lifecycle.currentState.name
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        //App in background

        Log.e(TAG, "************* backgrounded")
        Log.e(TAG, "************* ${isActivityVisible()}")
        Paper.book().write(Const.FOREGROUND, false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

        Log.e(TAG, "************* foregrounded")
        Log.e(TAG, "************* ${isActivityVisible()}")
        Paper.book().write(Const.FOREGROUND, true)
        // App in foreground
    }

}
