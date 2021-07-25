package com.manish.shaadi.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class ZipLiveData<T, R>(private val zipper: Zipper<T, R>) : MediatorLiveData<R>() {

    companion object {
        var result: MutableList<Any> = ArrayList<Any>()

        @Suppress("UNCHECKED_CAST")
        fun <T, R> create(
            resource: List<LiveData<out List<Any>>>,
            callback: Zipper<T, R>
        ): LiveData<T> {
            val liveData = ZipLiveData(zipper = callback)
            result.clear()

            for (source in resource) {
                liveData.addSource(source) { value ->
                    value?.let {
                        //                        value.mapIndexed { _, s -> result.add(s) }
                        result.add(it)
                        liveData.publish()
                    }
                }
            }

            return liveData as LiveData<T>
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun publish() {
        postValue(zipper.zip(result as List<T>))
    }

    interface Zipper<T, R> {

        fun zip(result: List<T>): R

    }
}