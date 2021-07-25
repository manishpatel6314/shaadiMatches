package com.manish.shaadi.binding.viewBinding

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.manish.shaadi.extensions.SpinnerExtensions
import com.manish.shaadi.extensions.SpinnerExtensions.setSpinnerEntries
import com.manish.shaadi.extensions.SpinnerExtensions.setSpinnerItemSelectedListener
import com.manish.shaadi.extensions.SpinnerExtensions.setSpinnerValue

class SpinnerBindings {

    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<Any>?) {
        setSpinnerEntries(entries)
    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setOnItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
        setSpinnerItemSelectedListener(itemSelectedListener)
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        setSpinnerValue(newValue)
    }
}