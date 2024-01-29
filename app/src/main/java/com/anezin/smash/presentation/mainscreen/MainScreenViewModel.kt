package com.anezin.smash.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anezin.smash.core.interfaces.LocalDataRepository
import com.anezin.smash.core.interfaces.MyIdRepository
import kotlinx.coroutines.launch
import java.util.UUID

class MainScreenViewModel(
    private val localDataRepository: LocalDataRepository,
    private val idRepository: MyIdRepository
):ViewModel() {
    val idState = idRepository.getMyId()
    fun getMyId() {
        viewModelScope.launch {
            idRepository.getMyId().collect {
                Log.d("id", it)
                if (it == "") {
                    val newId = UUID.randomUUID().toString()
                    idRepository.saveMyId(newId)
                    localDataRepository.saveMyId(newId)
                } else {
                    localDataRepository.saveMyId(it)
                }
            }
        }
    }
}

