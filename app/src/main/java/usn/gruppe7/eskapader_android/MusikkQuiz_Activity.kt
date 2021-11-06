package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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

        val queue = Volley.newRequestQueue(this)
        val json = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                var resultat = response[1].toString()
                val obj = JSONObject(resultat)
                val quizResponse = obj.getJSONArray("Musikkquiz")

                for(i in 0 until quizResponse.length()) {
                    val id = quizResponse.getJSONObject(i).getInt("Sporsmal_id")
                    val spørsmålTekst = quizResponse.getJSONObject(i).getString("Sporsmaltekst")
                    val svar = quizResponse.getJSONObject(i).getInt("Svar")
                    var musikkObjekt = Quiz(spørsmålTekst, id,svar)

                    val spørsmålListe = quizResponse.getJSONObject(i).getJSONArray("Alternativ")
                    for(i in 0 until spørsmålListe.length()) {
                        val spørsmål = spørsmålListe.get(i).toString();
                        musikkObjekt.addSpørsmål(spørsmål)
                    }

                    quizListe.add(musikkObjekt)
                    Log.d("FOR", "Laget objekt: $i ${musikkObjekt.toString()}")
                }
                //Her er listen data ferdig hentet

                val quizFragment = MusikkQuizFragment.newInstance(quizListe)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(container.id,quizFragment)
                transaction.commit()

            },
            { error ->
                Log.d("Feil i API kall: " , "Error: $error")
            }
        )

        // Add the request to the RequestQueue.
        Log.i("Test","La til bruh")
        queue.add(json)




    }
}


