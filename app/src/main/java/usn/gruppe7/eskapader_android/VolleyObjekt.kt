package usn.gruppe7.eskapader_android

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyObjekt(val appContext: Context) : Volley() {
    val url = "https://eskapader.herokuapp.com/spill"



    fun hentSpill_Dilemma( dillemmaListe : ArrayList<Dilemma>, spillNavn : String) {
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
                    val id = alleDilemma.getJSONObject(i).getInt("Spørsmål_id")
                    val tekst = alleDilemma.getJSONObject(i).getString("Spørsmåltekst")
                    val alt = alleDilemma.getJSONObject(i).getJSONArray("Alternativ")
                    val alt1 = alt.get(0).toString()
                    val alt2 = alt.get(1).toString()
                    val dilemmaObjekt = Dilemma(stat1, stat2,id,tekst,alt1,alt2);
                    dillemmaListe.add(dilemmaObjekt)
                }
            },
            {
                error ->
                println("Feil oppsto: $error")

            })


        queue.add(json)
        println("La til forespørsel i kø")

    }

    fun hentSpill_Quiz(url: String) {
        val queue = newRequestQueue(appContext)
        val json = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                    response ->
                val resultat = response.toString()
                println("Resultat= $resultat")

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


    fun toInteger(s: String): Int? {
        var value : Int? = null
        try {
            value = s.toInt()

        } catch (ex: NumberFormatException) {
            println("teksten gitt inneholdt ikke tall")
        }
        return value
    }




}

