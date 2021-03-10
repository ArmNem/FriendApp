package com.example.friendapp.MODEL

import android.content.Intent
import android.util.Log

object FriendsRepository {
    val mFriends = mutableListOf<BEFriend>(
        BEFriend("Jonas", "123", true),
        BEFriend("Anders", "1234", false),
    )

    fun getAll(): MutableList<BEFriend> = mFriends
    fun deleteFriendByName(name: String){
        val friend =   mFriends.find { friend -> friend.name == name }
        Log.d("TAG","friend deleted name = $name")
        mFriends.remove(friend)
    }
    fun findFriend(name: String?): BEFriend? {
      val friend =  mFriends.find { friend -> friend.name == name }
        return friend
    }
    fun createFriend(newfriend: BEFriend){

        Log.d("TAG","friend created $newfriend")
        mFriends.add(newfriend)
    }
    fun getAllNames(): Array<String>  =  mFriends.map { p -> p.name }.toTypedArray()
}