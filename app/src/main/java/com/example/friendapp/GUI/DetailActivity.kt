package com.example.friendapp.GUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.friendapp.MODEL.BEFriend
import com.example.friendapp.MODEL.FriendsRepository
import com.example.friendapp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    var iscreate = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (intent.extras == null) {
            iscreate = true
            tvName.text = (null)
            tvPhone.text = null
            BtnDelete.visibility = View.INVISIBLE
        }

        if (intent.extras != null) {
            iscreate = false
            val extras: Bundle = intent.extras!!
            val name = extras.getString("name")
            val phone = extras.getString("phone")
            val favorite = extras.getBoolean("favorite")
            tvName.setText(name)
            tvPhone.setText(phone)
            if (favorite)
                CheckFav.isChecked = true
            if (!favorite)
                CheckFav.isChecked = false
        }
        else
        {
            Log.d("xyz", "system error: intent.extras for detailactivity is null!!!!")
        }
        BtnDelete.setOnClickListener{v -> onClickDelete(v)}
    }


    fun onClickBack(view: View) { finish() }
    fun onClickSave(view: View) {
    if (iscreate == true) {
       val friendToCreate = BEFriend(name = tvName.text.toString(), phone = tvPhone.text.toString(), isFavorite = CheckFav.isChecked)
       val friends = FriendsRepository
       friends.createFriend(friendToCreate)
    }else{
        val friends = FriendsRepository
        val friendToUpdate = friends.findFriend(intent.extras?.getString("name"))
        if (friendToUpdate != null) {
            friendToUpdate.name = tvName.text.toString()
            friendToUpdate.phone = tvPhone.text.toString()
            friendToUpdate.isFavorite = CheckFav.isChecked
        }

    }
        finish()
    }

    fun onClickDelete(view: View) {
        val friends = FriendsRepository
        friends.deleteFriendByName(tvName.text.toString())
        finish()
    }

}