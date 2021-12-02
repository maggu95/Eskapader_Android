package usn.gruppe7.eskapader_android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import usn.gruppe7.eskapader_android.databinding.ActivityHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.ActivityLoginBinding


import java.net.URL

class HovedMenyActivity : AppCompatActivity() {

    private lateinit var currFragment : String
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding : ActivityHovedMenyBinding
    private lateinit var btLeggTilQuiz: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_hoved_meny)

        drawerLayout = binding.mainHost


        val navController = this.findNavController(R.id.MainHost)

        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navViewMain, navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.MainHost)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


}