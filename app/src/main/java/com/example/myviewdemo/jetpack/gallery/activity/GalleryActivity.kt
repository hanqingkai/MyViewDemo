package com.example.myviewdemo.jetpack.gallery.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myviewdemo.R

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
       NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.fragment))
    }


    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() ||findNavController(R.id.fragment).navigateUp()
    }
}