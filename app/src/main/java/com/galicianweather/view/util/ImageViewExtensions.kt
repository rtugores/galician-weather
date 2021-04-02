package com.galicianweather.view.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.galicianweather.injection.GlideApp

fun ImageView.setImageUrl(url: String, @DrawableRes errorRes: Int? = null) {
    if (url.isNotEmpty()) {
        val glideRequest = GlideApp
            .with(context)
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        errorRes?.let {
            glideRequest.error(it)
        }

        glideRequest.into(this)
    }
}
