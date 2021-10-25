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
    val quizListe = ArrayList<MusikkSpørsmål>()
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
                    var musikkObjekt = MusikkSpørsmål(spørsmålTekst, id,svar)

                    val spørsmålListe = quizResponse.getJSONObject(i).getJSONArray("Alternativ")
                    for(i in 0 until spørsmålListe.length()) {
                        val spørsmål = spørsmålListe.get(i).toString();
                        musikkObjekt.addSpørsmål(spørsmål)
                    }

                    quizListe.add(musikkObjekt)
                    Log.d("FOR", "Laget objekt: $i ${musikkObjekt.toString()}")
                }
                //Her er listen data ferdig hentet
                val bundle = Bundle().apply {
                    putParcelableArrayList("LIST", ArrayList<Parcelable>(quizListe))
                }
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
        queue.add(json)




        /* Henter border fra my_border.xml
        val shape: Drawable? = getDrawable(R.drawable.rounded_borders_musikkquiz)

        val sangtekst = findViewById<TextView>(R.id.sangtekst)
       // sangtekst.text = quizListe[0].getSpørsmålsTekst();

        val txt1 = findViewById<TextView>(R.id.musAlt1)
        // txt1.text = quizListe[0].getSpørsmål(0)
        txt1.setOnClickListener() {
            txt1.background = shape
        }

        val txt2 = findViewById<TextView>(R.id.musAlt2)
        txt2.setOnClickListener() {
            txt2.background = shape
        }

        val txt3 = findViewById<TextView>(R.id.musAlt3)
        txt3.setOnClickListener() {
            txt3.background = shape
        }

        val txt4 = findViewById<TextView>(R.id.musAlt4)
        txt4.setOnClickListener() {
            txt4.background = shape
        }


        val btBekreft = findViewById<Button>(R.id.btBekreft)



        btBekreft.setOnClickListener() {
            currSpørsmål++;
            sangtekst.setText(quizListe[currSpørsmål].getSpørsmålsTekst())
            txt1.setText(quizListe[currSpørsmål].getSpørsmål(0))
            txt2.setText(quizListe[currSpørsmål].getSpørsmål(1))
            txt3.setText(quizListe[currSpørsmål].getSpørsmål(2))
            txt4.setText(quizListe[currSpørsmål].getSpørsmål(3))

            txt1.setBackgroundResource(R.drawable.rounded_corner_view)
            txt2.setBackgroundResource(R.drawable.rounded_corner_view)
            txt3.setBackgroundResource(R.drawable.rounded_corner_view)
            txt4.setBackgroundResource(R.drawable.rounded_corner_view)
        }
        */
    }
}


