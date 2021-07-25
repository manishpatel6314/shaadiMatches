package com.manish.shaadi.binding.viewBinding

import android.view.View
import androidx.databinding.BindingAdapter

class BindingAdapters {

    @BindingAdapter("shouldVisible")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}