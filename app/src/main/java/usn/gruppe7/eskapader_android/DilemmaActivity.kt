package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.DilemmaLayoutBinding

class DilemmaActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")

    private lateinit var binding: DilemmaLayoutBinding
     var dilemmaListe = ArrayList<Dilemma>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.dilemma_layout)
        val container = binding.dilemmaContainer



        val connector = APIConnector(this)

        val valgtSpill = intent.extras?.getString("Spillnavn").toString()


         connector.hentSpill_DilemmaAsync( valgtSpill) {
            result ->
            if (result != null) {
                dilemmaListe = result
                val dilemmaFragment = DilemmaFragment.newInstance(dilemmaListe, valgtSpill)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(container.id, dilemmaFragment)
                transaction.commit()
            }
             else {
                 //Hvis vi ikke fikk hentet spill så går vi tilbake til hovedmeny
                Log.i("Dilemma Aktivity", "Fant ikke noe resultat i Dilemma")
                val intent = Intent(this, HovedMenyActivity::class.java)
                startActivity(intent)
            }
        }

    }
}