package org.mvp.uckmp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = UsercentricsOptions(
            settingsId = "gChmbFIdL",
            loggerLevel = UsercentricsLoggerLevel.DEBUG,
        )
        Usercentrics.initialize(this, options)

        setContent {
            App(this)
        }
    }
}
