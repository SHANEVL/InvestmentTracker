package com.example.investmenttracker.screens.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.investmenttracker.R
import android.content.res.Configuration
import android.widget.AdapterView
import android.widget.Spinner
import java.util.Locale

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val SETTINGS_FILE_NAME = "Settings"
        const val APP_LANG_KEY = "APP_LANG"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNavigateButton: Button = view.findViewById(R.id.btnNavigateButton)
        val btnVivesIdentity: Button = view.findViewById(R.id.btnVivesIdentity)
        val spinnerLanguage: Spinner = view.findViewById(R.id.spinner_language)

        btnNavigateButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_investmentActivity)
        }

        btnVivesIdentity.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_infoFragment)
        }

        val sharedPreferences = requireActivity().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString(APP_LANG_KEY, Locale.getDefault().language)
        spinnerLanguage.setSelection(
            resources.getStringArray(R.array.language_codes).indexOf(savedLanguage)
        )

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val languageCode = resources.getStringArray(R.array.language_codes)[position]
                if (languageCode != savedLanguage) { // Prevent unnecessary restarts
                    changeLanguage(languageCode)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Implementation not required
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        val sharedPreferences = requireActivity().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(APP_LANG_KEY, languageCode)
            apply()
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        // Update the configuration of the application context
        requireActivity().apply {
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        }

        // Restart the activity to apply the language
        activity?.recreate()
    }

}
