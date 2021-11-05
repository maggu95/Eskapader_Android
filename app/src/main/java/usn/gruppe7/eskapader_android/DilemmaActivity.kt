package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.DilemmaLayoutBinding
import usn.gruppe7.eskapader_android.databinding.MusikkquizSpillBinding

class DilemmaActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")

    private lateinit var binding: DilemmaLayoutBinding
    val dilemmaListe = ArrayList<Dilemma>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.dilemma_layout)
        val url = "https://eskapader.herokuapp.com/spill"
        binding = DataBindingUtil.setContentView(this, R.layout.dilemma_layout)
        val container = binding.dilemmaContainer

        val shape: Drawable? = getDrawable(R.drawable.rounded_borders_dilemma)
        val dilemma1 = findViewById<TextView>(R.id.dill_alt_1)
        val dilemma2 = findViewById<TextView>(R.id.dill_alt_2)
        val btNesteDilemma = findViewById<Button>(R.id.btNesteDilemma)

        val volley = VolleyObjekt(this)
        volley.hentSpill_Dilemma(dilemmaListe, "Dilemma")
        for(i in 0 until dilemmaListe.size)
            println(dilemmaListe.get(i))


        val dilemmaFragment =  DilemmaFragment.newInstance(dilemmaListe)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container.id,dilemmaFragment)
        transaction.commit()






    }
}