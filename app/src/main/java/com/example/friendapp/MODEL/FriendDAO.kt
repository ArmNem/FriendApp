package com.example.friendapp.MODEL

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FriendDAO {

    @Query("SELECT * from BEFriend order by id")
    fun getAll(): LiveData<List<BEFriend>>

    @Query("SELECT name from BEFriend order by name")
    fun getAllNames(): LiveData<List<String>>

    @Query("SELECT * from BEFriend where id = (:id)")
    fun getById(id: Int): LiveData<BEFriend>

    @Insert
    fun insert(p: BEFriend)

    @Update
    fun update(p: BEFriend)

    @Delete
    fun delete(p: BEFriend)

    @Query("DELETE from BEFriend")
    fun deleteAll()
}