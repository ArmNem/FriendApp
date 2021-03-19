package com.example.friendapp.GUI

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.friendapp.MODEL.BEFriend
import com.example.friendapp.MODEL.FriendsRepository
import com.example.friendapp.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {
    var iscreate = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (intent.extras == null) {
            iscreate = true
            tvName.text = (null)
            tvPhone.text = null
            tvEmail.text = null
            tvSource.text = null
            BtnDelete.visibility = View.INVISIBLE
        }

        if (intent.extras != null) {
            iscreate = false
            val extras: Bundle = intent.extras!!
            val name = extras.getString("name")
            val phone = extras.getString("phone")
            val favorite = extras.getBoolean("favorite")
            val email = extras.getString("email")
            val source = extras.getString("source")
            tvName.setText(name)
            tvPhone.setText(phone)
            tvEmail.setText(email)
            tvSource.setText(source)
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
       val friendToCreate = BEFriend(name = tvName.text.toString(), phone = tvPhone.text.toString(), isFavorite = CheckFav.isChecked, email = tvEmail.text.toString(), source = tvSource.text.toString())
       val friends = FriendsRepository
       friends.createFriend(friendToCreate)
    }else{
        val friends = FriendsRepository
        val friendToUpdate = friends.findFriend(intent.extras?.getString("name"))
        if (friendToUpdate != null) {
            friendToUpdate.name = tvName.text.toString()
            friendToUpdate.phone = tvPhone.text.toString()
            friendToUpdate.email = tvEmail.text.toString()
            friendToUpdate.source = tvSource.text.toString()
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
    fun onClickCALL(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${tvPhone.text}")
        startActivity(intent)
    }
    fun onClickEMAIL(view: View) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val receivers = tvEmail.text.toString()
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test")
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Hej, Hope that it is ok, Best Regards android...;-)")
        startActivity(emailIntent)
    }
    fun onClickBROWSER(view: View) {
        val url = tvSource.text.toString()
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
    fun onClickSMS(view: View) {
        showYesNoDialog()
    }
    private fun showYesNoDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("SMS Handling")
        alertDialogBuilder
                .setMessage("Click Direct if SMS should be send directly. Click Start to start SMS app...")
                .setCancelable(true)
                .setPositiveButton("Direct") { dialog, id -> sendSMSDirectly() }
                .setNegativeButton("Start", { dialog, id -> startSMSActivity() })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    val PERMISSION_REQUEST_CODE = 1
    private fun sendSMSDirectly() {
        Toast.makeText(this, "An sms will be send", Toast.LENGTH_LONG)
                .show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("a", "permission denied to SEND_SMS - requesting it")
                val permissions = arrayOf(Manifest.permission.SEND_SMS)
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
                return
            } else Log.d("a", "permission to SEND_SMS granted!")
        } else Log.d("a", "Runtime permission not needed")
        sendMessage()
    }
    private fun startSMSActivity() {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("sms:${tvPhone.text}")
        sendIntent.putExtra("sms_body", "Hi, it goes well on the android course...")
        startActivity(sendIntent)
    }
    private fun sendMessage() {
        val m = SmsManager.getDefault()
        val text = "Hi, it goes well on the android course..."
        m.sendTextMessage(tvPhone.text.toString(), null, text, null, null)
    }

    fun onClickCamera(view: View) {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}