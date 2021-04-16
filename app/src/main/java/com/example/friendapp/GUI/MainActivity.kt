package com.example.friendapp.GUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.friendapp.MODEL.BEFriend
import com.example.friendapp.MODEL.FriendRepoInDB
import com.example.friendapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FriendRepoInDB.initialize(this)
        setupDataObserver()
    }
    //var cache: List<BEFriend>? = null;
    private fun setupDataObserver() {
        val mRep = FriendRepoInDB.get()
            val nameObserver = Observer<List<BEFriend>> { persons ->
                //cache = persons;
                val asStrings = persons.map { p -> "${p.id}: ${p.name}" }
                val adapter: ListAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    asStrings.toTypedArray()
                )
                friendList.adapter = adapter
                Log.d("abc", "Listview updated")
            }
            mRep.getAllLiveData().observe(this, nameObserver)
        friendList.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ -> onListItemClick(pos)}
    }
     fun onListItemClick(pos: Int) {
       // val id = cache!![pos].id
        //Toast.makeText(this, "You have clicked $name at position $pos", Toast.LENGTH_LONG).show()
         val mRep = FriendRepoInDB.get()
         val friend = mRep.getByPos(pos)
            if (friend != null)
            {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("friend",friend)
                intent.putExtra("id", friend.id )
                /*
                intent.putExtra("name", friend.name )
                intent.putExtra("phone", friend.phone)
                intent.putExtra("favorite", friend.isFavorite)
                intent.putExtra("email",friend.email)
                intent.putExtra("source",friend.source)*/
                startActivity(intent)
            }
     }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()

        when (id) {
            R.id.createFriend -> {
                Toast.makeText(this, "Create friend selected...", Toast.LENGTH_LONG).show()
                true
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("newFriend","empty")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
}