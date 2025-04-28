package com.daniel.tvlauncher

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter

class CardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val cardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setOnFocusChangeListener { v, hasFocus ->
                v.animate().scaleX(if (hasFocus) 1.1f else 1f)
                    .scaleY(if (hasFocus) 1.1f else 1f)
                    .setDuration(200).start()
            }
        }
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val card = item as AppCard
        val cardView = viewHolder.view as ImageCardView

        cardView.titleText = card.title
        cardView.setMainImageDimensions(200, 200)
        cardView.mainImage = card.icon
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.mainImage = null
    }
}