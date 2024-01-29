package com.anezin.smash.infrastructure.actions

import com.anezin.smash.core.interfaces.MyIdRepository
import kotlinx.coroutines.flow.Flow

class GetMyIdFromSharedPrefs(
    private val myIdRepository: MyIdRepository
){
    operator fun invoke(): Flow<String> {
        return myIdRepository.getMyId()
    }
}
//myIdRepository.getMyId().collect {
//    myId = it
//    if(myId.isEmpty()) {
//        newId = UUID.randomUUID().toString()
//        myIdRepository.saveMyId(newId)
//        myId = newId
//    }
//}