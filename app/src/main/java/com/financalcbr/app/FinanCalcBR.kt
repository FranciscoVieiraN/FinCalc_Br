package com.financalcbr.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.financalcbr.app.utils.ConstantsUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FinanCalcBR : Application() {

    companion object{
        private lateinit var instance: FinanCalcBR

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            instance = this
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            val tag = tag ?: ConstantsUtils.TAG
            Log.println(priority, tag, message)
            t?.let {
                Log.println(priority, tag, Log.getStackTraceString(it))
            }
        }
    }
}