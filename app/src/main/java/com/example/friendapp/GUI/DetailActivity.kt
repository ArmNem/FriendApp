package com.example.friendapp.GUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.friendapp.MODEL.FriendsRepository
import com.example.friendapp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.extras != null) {
            val extras: Bundle = intent.extras!!
            val name = extras.getString("name")
            val phone = extras.getString("phone")
            val favorite = extras.getBoolean("favorite")
            tvName.setText(name)
            tvPhone.setText(phone)
            imgFavorite.setImageResource(if (favorite) R.drawable.ok else R.drawable.notok)
        }
        else
        {
            Log.d("xyz", "system error: intent.extras for detailactivity is null!!!!")
        }
        BtnDelete.setOnClickListener{v -> onClickDelete(v)}
    }


    fun onClickBack(view: View) { finish() }
    fun onClickSave(view: View) {

    }

    fun onClickDelete(view: View) {
        val friends = FriendsRepository
        friends.deleteFriendByName(tvName.text.toString())
        finish()
    }

}