package com.example.task7.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.task7.ClevertecApp
import com.example.task7.di.AppComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

val Context.appComponent: AppComponent
    get() = when (this) {
        is ClevertecApp -> this.appComponent
        else -> this.applicationContext.appComponent
    }

suspend fun ImageView.loadImage(url: String) {
    val g = Glide.with(this@loadImage.context)
        .load(url)
    withContext(Dispatchers.Main) {
        if (currentCoroutineContext().isActive) {
            g.into(this@loadImage)
        }
    }
}
