package usn.gruppe7.eskapader_android

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
        //setContentView(R.layout.activity_hoved_meny)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hoved_meny)

        drawerLayout = binding.mainHost

        val navController = this.findNavController(R.id.MainHost)

        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navViewMain, navController)


        btLeggTilQuiz = findViewById(R.id.btLeggTilQuiz)
        btLeggTilQuiz.setOnClickListener { view : View ->
            val popupMenu: PopupMenu = PopupMenu(this, btLeggTilQuiz)
            popupMenu.menuInflater.inflate(R.menu.item_ny_quiz, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener( PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_musikkquiz ->
                        view.findNavController().navigate(R.id.action_hovedMenyFragment_to_opprettMusikkQuizFragment)
                    R.id.item_dilemma ->
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popupMenu.show()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.MainHost)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


}