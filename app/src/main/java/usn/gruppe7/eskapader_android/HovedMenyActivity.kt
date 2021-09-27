package usn.gruppe7.eskapader_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import usn.gruppe7.eskapader_android.databinding.ActivityHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.ActivityLoginBinding

class HovedMenyActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding : ActivityHovedMenyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_hoved_meny)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hoved_meny)

        drawerLayout = binding.mainHost

        val navController = this.findNavController(R.id.MainHost)
        Log.d("Test", drawerLayout.toString())


        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navViewMain, navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.MainHost)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}