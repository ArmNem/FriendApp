package com.example.friendapp.MODEL

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class BEFriend(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var name: String,
    var phone: String?,
    var isFavorite: Boolean,
    var email: String?,
    var source: String?,
    var picPath: String?,
    var location: String?
    )
    :Serializable