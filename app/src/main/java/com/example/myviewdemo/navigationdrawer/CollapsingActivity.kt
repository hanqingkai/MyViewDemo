package com.example.myviewdemo.navigationdrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myviewdemo.R
import kotlinx.android.synthetic.main.activity_collapsing.*
import kotlinx.android.synthetic.main.content_layout.*

class CollapsingActivity : AppCompatActivity() {
    //配置抽屉上的控制条
    private lateinit var appBarConfiguration: AppBarConfiguration

    //配置导航控制器
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.fragment)
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        val set = setOf(R.id.listFragment, R.id.pagerFragment, R.id.textFragment)
        appBarConfiguration = AppBarConfiguration(set, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}