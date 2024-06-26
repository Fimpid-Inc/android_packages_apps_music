package it.fast4x.rimusic.ui.screens.player

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi

import it.fast4x.rimusic.R
import it.fast4x.rimusic.enums.PlayerVisualizerType

import it.fast4x.rimusic.extensions.visualizer.Visualizer
import it.fast4x.rimusic.utils.playerVisualizerTypeKey
import it.fast4x.rimusic.utils.rememberPreference

import it.fast4x.rimusic.utils.toast

@UnstableApi
@Composable
fun ShowVisualizer(
    //mediaId: String,
    isDisplayed: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    var playerVisualizerType by rememberPreference(playerVisualizerTypeKey, PlayerVisualizerType.Disabled)
    //Log.d("visualizer player","passato da qui")

    if (playerVisualizerType != PlayerVisualizerType.Disabled){
    //val (colorPalette, typography) = LocalAppearance.current
    //val context = LocalContext.current
    //val binder = LocalPlayerServiceBinder.current ?: return
        //Log.d("visualizer player","è attivo da qui")

    val activity = LocalContext.current as Activity
    //VisualizerComputer.setupPermissions( LocalContext.current as Activity)
    if (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        LocalContext.current.toast(stringResource(R.string.require_mic_permission))
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.RECORD_AUDIO), 42
        )
    } else {
        AnimatedVisibility(
            visible = isDisplayed,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500)),
        ) {
            Visualizer(
                showInPage = false,
                playerVisualizerType = playerVisualizerType
            )
        }

    }

}

}
