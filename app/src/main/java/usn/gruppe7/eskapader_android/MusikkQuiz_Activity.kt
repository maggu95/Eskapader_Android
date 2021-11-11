package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import usn.gruppe7.eskapader_android.databinding.MusikkquizSpillBinding


class MusikkQuiz_Activity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")
    val quizListe = ArrayList<Quiz>()
    private lateinit var binding: MusikkquizSpillBinding
    private var currSpørsmål : Int = 0
    private val url = "https://eskapader.herokuapp.com/spill"

    init {
        currSpørsmål = 0;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.musikkquiz_spill)
        binding = DataBindingUtil.setContentView(this, R.layout.musikkquiz_spill)
        val container = binding.musikkQuizContainer



        val connector = APIConnector(this)
        connector.hentSpill_QuizAsync("Musikkquiz") {
            result ->
            if(result != null) {
                val quizFragment = MusikkQuizFragment.newInstance(result)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(container.id,quizFragment)
                transaction.commit()
            }
            else {
                Toast.makeText(this, "Noe gikk galt med innhentinh av spill", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HovedMenyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}


