package huitca1212.galicianweather.view.util

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import huitca1212.galicianweather.injection.GlideApp

fun ImageView.setImageUrl(url: String) {
    if (url.isNotEmpty()) {
        GlideApp
            .with(context)
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
    }
}