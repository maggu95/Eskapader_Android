package usn.gruppe7.eskapader_android

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class APIConnector(val appContext: Context) : Volley() {
    val url = "https://eskapader.herokuapp.com/spill"


    fun hentSpill_DilemmaAsync(spillNavn : String, callBack: (result: ArrayList<Dilemma>?) -> Unit)  {

        var dilemmaListe = ArrayList<Dilemma>()

        val queue = newRequestQueue(appContext)
        val json = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                response ->
                var resultat : String? = null

                // Finn spill objektet fra api
                for( i in 0 until response.length()) {
                    val navn  = response.getJSONObject(i).getString("Spillnavn")
                    if(navn == spillNavn){
                        resultat = response[i].toString();
                    }
                }
                if(resultat.isNullOrEmpty()) {
                    throw error("Fant ikke et spill med navn oppgitt")
                }

                // Parse resultat til JSON objekt
                val objekt = JSONObject(resultat)
                val alleDilemma = objekt.getJSONArray("Dilemma")

                // Gå gjennom alle dilemmaer i spillet, lag objekt av disse og lagre i dilemma listen
                for(i in 0 until alleDilemma.length()) {

                    val stat1 = alleDilemma.getJSONObject(i).getJSONArray("Statistikk").getInt(0)
                    val stat2 = alleDilemma.getJSONObject(i).getJSONArray("Statistikk").getInt(1)
                    var stat = arrayOf(stat1,stat2)
                    val id = alleDilemma.getJSONObject(i).getInt("Spørsmål_id")
                    val tekst = alleDilemma.getJSONObject(i).getString("Spørsmåltekst")
                    val alt = alleDilemma.getJSONObject(i).getJSONArray("Alternativ")
                    val alt1 = alt.get(0).toString()
                    val alt2 = alt.get(1).toString()
                    val altListe = arrayOf(alt1,alt2)
                    val dilemmaObjekt = Dilemma(stat,id,tekst,altListe);
                    dilemmaListe.add(dilemmaObjekt)
                    Log.i("test", "La til i liste -> $dilemmaObjekt")
                }
                callBack.invoke(dilemmaListe)

            },
            {
                error ->
                println("Feil oppsto: $error")

            })


        queue.add(json)

    }

    fun hentSpill_QuizAsync(spillNavn: String , callBack: (result: ArrayList<Quiz>?) -> Unit) {
        val queue = newRequestQueue(appContext)
        var quizListe = ArrayList<Quiz>()
        val json = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                    response ->

                var resultat : String? = null

                // Finn spill objektet fra api
                for( i in 0 until response.length()) {
                    val navn  = response.getJSONObject(i).getString("Spillnavn")
                    if(navn == spillNavn){
                        resultat = response[i].toString();
                    }
                }
                if(resultat.isNullOrEmpty()) {
                    throw error("Fant ikke et spill med navn oppgitt")
                }

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

                }
                //Ferdig med å behandle quiz spørsmål...
                callBack.invoke(quizListe)

            },
            {
                    error ->
                println("Feil oppsto: $error")

            })


        queue.add(json)
        println("La til forespørsel i kø")

    }


    fun vekkAPI() {
        val queue = newRequestQueue(appContext)
        val json = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                    response ->
                    if(!response.isNull(0))
                    println("Vekket api")

            },
            {
                    error ->
                    println("Fikk ikke vekket api: $error")

            })


        queue.add(json)
    }

    fun opprettDIlemmaSpill(author : String, spillNavn: String, dilemmaListe : ArrayList<Dilemma>) {
        val postURL = "https://eskapader.herokuapp.com/leggDilemma"
        val requestQueue = newRequestQueue(appContext)
        val postData = JSONObject()

        postData.put("Spillnavn", spillNavn)
        postData.put("Author", author)

        for(i in 0 until dilemmaListe.size){
            val test = JSONObject();
            test.put("Statistikk", dilemmaListe[i].statistikk)
            test.put("Alternativer", dilemmaListe[i].alternativ)
            test.put("SpørsmålsTekst", dilemmaListe[i].tekst)
            test.put("Spørsmål_id", dilemmaListe[i].id)
            postData.put("Dilemma", test)
        }

        val req = JsonObjectRequest(Request.Method.POST,postURL,postData,
            {
                response ->
                val resultat = response.toString(4)
                println(resultat)


            },
            {
                error ->
                println("Feil oppsto i POST: $error")
            }
        )
        requestQueue.add(req)











    }

    fun opprettQuizSpill(author : String, spillNavn: String, quizListe : ArrayList<Quiz>) {
        val postURL = "https://eskapader.herokuapp.com/leggQuiz"
        val requestQueue = newRequestQueue(appContext)

        val postData = JSONObject()




    }



}

