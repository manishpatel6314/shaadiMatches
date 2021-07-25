package com.manish.shaadi.shaadimatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.manish.shaadi.remoteUtils.Resource
import com.manish.shaadi.shaadimatch.data.MatchData
import com.manish.shaadi.shaadimatch.repo.MatchRepository
import javax.inject.Inject

class MatchesViewModel @Inject constructor(
    var repo : MatchRepository
) : ViewModel() {

    private val _trigger  = MutableLiveData<String>()
    val result : LiveData<Resource<List<MatchData>>> = Transformations.switchMap(_trigger){
        repo.fetchMatchesDetails(10)
    }

    init {
        _trigger.value = "fetch"
    }

    fun updateMatchAction(action : String , userName : String){
        repo.saveUserResponse(action, userName)
    }

}