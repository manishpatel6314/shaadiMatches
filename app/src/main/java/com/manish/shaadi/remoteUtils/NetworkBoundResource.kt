package com.manish.shaadi.remoteUtils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.manish.shaadi.helper.AppExecutors

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        appExecutors.diskIO().execute {
            val dbSource = loadFromDb()
            appExecutors.mainThread().execute {
                result.addSource(dbSource) { resultType ->
                    result.removeSource(dbSource)
                    if (shouldFetch(resultType)) {
                        fetchFromNetwork(dbSource)
                    } else {
                        result.addSource(dbSource) { rT ->
                            result.value = Resource.localData(rT)
                        }
                    }
                }
            }
        }
    }


    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        appExecutors.diskIO().execute {
            val apiResponse = createCall()
            // we re-attach dbSource as a new source, it will dispatch its latest value quickly

//        Logger.d(Thread.currentThread(), "Call: $apiResponse")
            appExecutors.mainThread().execute {
                result.addSource(dbSource) { resultType ->
                    setValue(Resource.loading(resultType))
                }

//                Logger.e(Thread.currentThread(), "APi_Ka_Response ${Gson().toJson(apiResponse)}")
//                print("fetch_response: ${Gson().toJson(apiResponse)}")

                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    result.removeSource(dbSource)
//                    Logger.e(
//                        Thread.currentThread(),
//                        "APi_Ka_Response after source ${Gson().toJson(response)}"
//                    )//todo remove gson

                    when (response) {
                        is ApiSuccessResponse -> {
                            appExecutors.diskIO().execute {
                                processResponse(response)?.let { saveCallResult(it) }
                                val dbData = loadFromDb()
                                appExecutors.mainThread().execute {
                                    result.addSource(dbData) { newData ->
                                        setValue(Resource.success(newData))
                                    }
                                }
                            }
                        }
                        is ApiEmptyResponse -> {
                            appExecutors.diskIO().execute {
                                val dbData = loadFromDb()
                                appExecutors.mainThread().execute {
                                    // reload from disk whatever we had
                                    result.addSource(dbData) { newData ->
                                        setValue(Resource.success(newData))
                                    }
                                }
                            }

                        }
                        is ApiErrorResponse -> {
                            onFetchFailed()
                            result.addSource(dbSource) { newData ->
                                setValue(
                                    Resource.error(
                                        response.errorMessage,
                                        newData,
                                        response.code
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: ApiSuccessResponse<RequestType>): RequestType? {
        return response.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @WorkerThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @WorkerThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @WorkerThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun uploadTag(): String?
}