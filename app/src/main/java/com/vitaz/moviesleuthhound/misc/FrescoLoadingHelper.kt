package com.vitaz.moviesleuthhound.misc

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.view.SimpleDraweeView
import com.vitaz.moviesleuthhound.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FrescoLoadingHelper @Inject constructor(@ApplicationContext val context: Context) {
    fun setUri(draweeView: SimpleDraweeView, uri: Uri, retryEnabled: Boolean) {
        draweeView.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(draweeView.controller)
            .setTapToRetryEnabled(retryEnabled)
            .setUri(uri)
            .setAutoPlayAnimations(true)
            .build()
    }

    fun getFrescoProgressBarLoadable(): ProgressBarDrawable {
        return ProgressBarDrawable().apply {
            color = ContextCompat.getColor(context, R.color.accent)
            backgroundColor = ContextCompat.getColor(
                context,
                android.R.color.white
            )
            radius = 20
        }
    }
}


