package com.example.friendapp.GUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.example.friendapp.MODEL.FriendsRepository
import com.example.friendapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
    override fun onResume() {
        super.onResume()
        val friends = FriendsRepository
        val friendNames = friends.getAllNames()

        val adapter: ListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, friendNames
        )

        friendList.adapter = adapter

        friendList.setOnItemClickListener { _, _, position, _ -> onListItemClick(position) }
    }
    fun onListItemClick( position: Int ) {
        // position is in the list!
        // first get the name of the person clicked
        /*val name = Friends().getAll()[position].name
        // and a greeting
        Toast.makeText(
            this,
            "Hi $name! Have you done your homework?",
            Toast.LENGTH_LONG
        ).show()*/
        val intent = Intent(this, DetailActivity::class.java)
        val friend = FriendsRepository.getAll()[position]

        intent.putExtra("name", friend.name )
        intent.putExtra("phone", friend.phone)
        intent.putExtra("favorite", friend.isFavorite)
        startActivity(intent)

    }
}