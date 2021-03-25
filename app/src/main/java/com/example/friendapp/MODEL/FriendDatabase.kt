package com.example.friendapp.MODEL

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BEFriend::class], version=1)
abstract class FriendDatabase : RoomDatabase() {

    abstract fun personDao(): FriendDAO
}