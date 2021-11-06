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
       // setContentView(R.layout.dilemma_layout)
        binding = DataBindingUtil.setContentView(this, R.layout.dilemma_layout)
        val container = binding.dilemmaContainer


        val connector = APIConnector(this)

         connector.hentSpill_DilemmaAsync( "Dilemma") {
            result ->
            if (result != null) {
                dilemmaListe = result
                val dilemmaFragment = DilemmaFragment.newInstance(dilemmaListe)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(container.id, dilemmaFragment)
                transaction.commit()
            }
             else {
                Log.i("Dilemma Aktivity", "Fant ikke noe resultat i Dilemma")
                val intent = Intent(this, HovedMenyActivity::class.java)
                startActivity(intent)
            }
        }

    }
}