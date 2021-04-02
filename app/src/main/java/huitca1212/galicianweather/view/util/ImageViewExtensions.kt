package huitca1212.galicianweather.view.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.engine.DiskCacheStrategy
import huitca1212.galicianweather.injection.GlideApp

fun ImageView.setImageUrl(url: String, @DrawableRes placeholderRes: Int? = null) {
    if (url.isNotEmpty()) {
        val glideRequest = GlideApp
            .with(context)
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        placeholderRes?.let {
            glideRequest.placeholder(it)
        }

        glideRequest.into(this)
    }
}
