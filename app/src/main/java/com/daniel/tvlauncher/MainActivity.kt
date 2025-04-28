package com.daniel.tvlauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isDefaultLauncher()) {
            showLauncherChooser()
            finish()
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MainFragment())
            .commit()
    }

    private fun isDefaultLauncher(): Boolean {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        return packageManager.resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )?.activityInfo?.packageName == packageName
    }

    private fun showLauncherChooser() {
        startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
        Toast.makeText(this, "Please set this launcher as default", Toast.LENGTH_LONG).show()
    }
}