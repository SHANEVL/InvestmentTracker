// BaseActivity.kt
package com.example.investmenttracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Configuration
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
// track the language setting for the whole app
    companion object {
        const val SETTINGS_FILE_NAME = "Settings"
        const val APP_LANG_KEY = "APP_LANG"
    }

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(APP_LANG_KEY, Locale.getDefault().language)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    protected fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
