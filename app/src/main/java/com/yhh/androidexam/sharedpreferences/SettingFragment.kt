package com.yhh.androidexam.sharedpreferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.yhh.androidexam.R


class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}