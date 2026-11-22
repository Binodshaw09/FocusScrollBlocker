package com.example.focusscrollblocker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("FocusPrefs", Context.MODE_PRIVATE)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(64, 128, 64, 64)
        }
        val title = TextView(this).apply {
            text = "Focus Scroll Blocker"
            textSize = 24f
            setPadding(0, 0, 0, 48)
        }
        val focusSwitch = Switch(this).apply {
            text = "Focus Mode (Block Reels & Shorts)  "
            textSize = 16f
            isChecked = prefs.getBoolean("focus_mode", false)
            setOnCheckedChangeListener { _, isChecked ->
                prefs.edit().putBoolean("focus_mode", isChecked).apply()
            }
        }
        val permissionBtn = Button(this).apply {
            text = "Enable Accessibility Service"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        }
        layout.addView(title)
        layout.addView(focusSwitch)
        layout.addView(permissionBtn)
        setContentView(layout)
    }
}
