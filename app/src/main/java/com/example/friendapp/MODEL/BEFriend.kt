package com.example.friendapp.MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class BEFriend(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var name: String,
    var phone: String,
    var isFavorite: Boolean,
    var email: String,
    var source: String)
    :Serializable