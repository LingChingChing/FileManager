package com.example.filemanager

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var storageBtn: MaterialButton = findViewById(R.id.storageBtn)
        storageBtn.setOnClickListener(View.OnClickListener(){
             fun onCLick(v: View){
                 if (checkPermission()){
                     var intent: Intent = Intent(this@MainActivity, FileListActivity::class.java)
                     var path: String = Environment.getExternalStorageDirectory().getPath()
                     intent.putExtra("path", path)
                     startActivity(intent)
                 } else {
                     requestPermission()
                 }
             }
        })

    }
    fun checkPermission(): Boolean {
        var result:Int = ContextCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }
    @SuppressLint("SuspiciousIndentation")
    private fun requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(this@MainActivity,"Storage permission is requires, please allow from settings",Toast.LENGTH_SHORT).show()
        } else
        ActivityCompat.requestPermissions(this@MainActivity , arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)
    }
}