package com.example.friendapp.GUI

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.friendapp.MODEL.BEFriend
import com.example.friendapp.MODEL.FriendRepoInDB
import com.example.friendapp.R
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
import kotlin.math.log

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
            tvLocation.text = null
            BtnDelete.visibility = View.INVISIBLE
        }

        if (intent.extras != null) {
            iscreate = false
            val extras: Bundle = intent.extras!!
            /*var id = extras.get("id")
            val name = extras.getString("name")
            val phone = extras.getString("phone")
            val favorite = extras.getBoolean("favorite")
            val email = extras.getString("email")
            val source = extras.getString("source")*/
            val friend = extras["friend"]
            as BEFriend
            tvName.setText(friend.name)
            tvPhone.setText(friend.phone)
            tvEmail.setText(friend.email)
            tvSource.setText(friend.source)
            tvLocation.setText(friend.location)
            if (friend.isFavorite)
                CheckFav.isChecked = true
            if (!friend.isFavorite)
                CheckFav.isChecked = false

            Log.d("abc","" + friend)
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
       val friendToCreate = BEFriend(id = 0,name = tvName.text.toString(), phone = tvPhone.text.toString(), isFavorite = CheckFav.isChecked, email = tvEmail.text.toString(), source = tvSource.text.toString(), location = tvLocation.text.toString())
        val mRep = FriendRepoInDB.get()
        mRep.insert(friendToCreate)
        Log.d("abc","Friend created")
    }else{
        val mRep = FriendRepoInDB.get()
        var friendToUpdate = mRep.getById(intent.extras!!.get("id") as Int)
        Log.d("a", friendToUpdate.toString())
        if (friendToUpdate != null) {
            friendToUpdate = BEFriend(id = intent.extras!!.get("id") as Int, name = tvName.text.toString(), phone = tvPhone.text.toString(), isFavorite = CheckFav.isChecked, email = tvEmail.text.toString(),source = tvSource.text.toString(), location = tvLocation.text.toString())
            mRep.update(friendToUpdate)
            Log.d("abc","Friend updated" + friendToUpdate)
        }
    }
        finish()
    }

    fun onClickDelete(view: View) {
        val mRep = FriendRepoInDB.get()
        val id = intent.extras!!.getInt("id")
        var friendToDelete = mRep.getById(id)
        if (friendToDelete !=null) {
            mRep.delete(friendToDelete)
        }
        Log.d("abc","Friend deleted" + friendToDelete)
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
        startActivityForResult(intent,5)
    }
   /* @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val mImage = findViewById<ImageView>(R.id.imageView)
        if (requestCode == 5) {
            if (resultCode == 10)
                    //showImageFromFile(mImage,fil)

        }
    }*/
    private fun showImageFromFile(img: ImageView, f: File) {
        img.setImageURI(Uri.fromFile(f))
        imageView.setImageURI(Uri.fromFile(f))
        //mImage.setRotation(90);

    }
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    @SuppressLint("MissingPermission")
    fun onClickGetLocation(view: View) {
        requestPermissions()
        if(!isPermissionGiven()){
            Toast.makeText(this, "Permission to use GPS is denied", Toast.LENGTH_LONG).show()
            return
        }
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if(location != null){
            tvLocation.text = "${location.latitude}, ${location.longitude}"
        } else{
            Toast.makeText(this, "The Location is unfortunately null", Toast.LENGTH_LONG).show()
        }
    }

    private fun isPermissionGiven(): Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return permissions.all {f -> checkSelfPermission(f) == PackageManager.PERMISSION_GRANTED}
        }
        return true
    }
    private fun requestPermissions(){
        if(!isPermissionGiven()){
            Log.d("TAG", "Permission denied to use GPS - requesting it")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(permissions, 1)
            } else
                Log.d("TAG", "Permission to use GPS Granted")
        }
    }

    fun onClickShowMaps(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        //intent.data = Uri.parse("${tvLocation.text}")
        intent.putExtra("location", tvLocation.text)
        //intent.putExtra("id", friend.id )
        startActivity(intent)
//        val sendIntent = Intent(Intent.ACTION_VIEW)
//        sendIntent.data = Uri.parse("sms:${tvPhone.text}")
//        sendIntent.putExtra("sms_body", "Hi, it goes well on the android course...")
//        startActivity(sendIntent)
        if(tvLocation == null){
            Toast.makeText(this, "There is no location for this friend", Toast.LENGTH_SHORT)
        }
    }
}