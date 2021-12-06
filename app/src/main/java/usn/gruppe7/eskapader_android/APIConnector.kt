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
import org.json.JSONArray


class APIConnector(val appContext: Context) : Volley() {
    val url = "https://eskapader.herokuapp.com/spill"


    fun hentAlleSpill(callBack: (result: ArrayList<String>?) -> Unit){
        var spillListe : ArrayList<String> = ArrayList()
        val queue = newRequestQueue(appContext)

        val req = JsonArrayRequest(
            Request.Method.GET,url,null,
            {
                response ->
                for(i in 0 until response.length() ) {
                    spillListe.add(response.getJSONObject(i).getString("Spillnavn"))
                }
                callBack.invoke(spillListe)
            },
            {
                error ->
                println("$error")
            }

        )
        queue.add(req)
    }


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
                    val tekst = alleDilemma.getJSONObject(i).getString("SpørsmålsTekst")
                    var alt : JSONArray? = null
                    if(spillNavn == "Dilemma")
                        alt = alleDilemma.getJSONObject(i).getJSONArray("Alternativ")
                    else {
                        alt = alleDilemma.getJSONObject(i).getJSONArray("Alternativer")
                    }
                    val alt1 = alt.get(0).toString()
                    val alt2 = alt.get(1).toString()
                    val altListe = arrayOf(alt1,alt2)
                    val dilemmaObjekt = Dilemma(stat,id,tekst,altListe);
                    dilemmaListe.add(dilemmaObjekt)
                }
                callBack.invoke(dilemmaListe)

            },
            {
                error ->
                println("Feil oppsto: $error")

            })


        queue.add(json)

    }

    fun hentGlobalMusikkQuiz(callBack: (result: ArrayList<Quiz>?) -> Unit) {
        val queue = newRequestQueue(appContext)
        var quizListe = ArrayList<Quiz>()
        val json = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                    response ->
                var resultat : JSONObject? = null
                for(i in 0 until response.length())
                    if(response.getJSONObject(i).getString("Spillnavn") == "Musikkquiz")
                        resultat = response.getJSONObject((i))

                if(resultat == null) {
                    println("Fant ikke global Musikkquiz")
                }
                else {
                    val musikkQuizArr = resultat.getJSONArray("Musikkquiz")
                    for(i in 0 until musikkQuizArr.length()){
                        println("Går gjennom quiz arr nr -> $i")
                        val id = musikkQuizArr.getJSONObject(i).getInt("Spørsmål_id")
                        println("Fant id -> $id" )
                        val spørsmålTekst = musikkQuizArr.getJSONObject(i).getString("Spørsmål")
                        val svar = musikkQuizArr.getJSONObject(i).getInt("Svar")
                        var musikkObjekt = Quiz(spørsmålTekst, id,svar)

                        val spørsmålListe = musikkQuizArr.getJSONObject(i).getJSONArray("Alternativer")
                        for(i in 0 until spørsmålListe.length()) {
                            val spørsmål = spørsmålListe.get(i).toString();
                            musikkObjekt.addSpørsmål(spørsmål)
                        }
                        quizListe.add(musikkObjekt)
                    }
                    callBack.invoke(quizListe)

                }
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
                val quizResponse = obj.getJSONArray("Spørsmål")


                for(i in 0 until quizResponse.length()) {
                    val id = quizResponse.getJSONObject(i).getInt("Spørsmål_id")
                    val spørsmålTekst = quizResponse.getJSONObject(i).getString("SpørsmålsTekst")
                    val svar = quizResponse.getJSONObject(i).getInt("Svar")
                    var musikkObjekt = Quiz(spørsmålTekst, id,svar)

                    val spørsmålListe = quizResponse.getJSONObject(i).getJSONArray("Alternativer")
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
        val DilemmaArray = JSONArray();

        for(i in 0 until dilemmaListe.size){
            val test = JSONObject();

            val statArr = JSONArray();
            statArr.put(dilemmaListe[i].statistikk[0])
            statArr.put(dilemmaListe[i].statistikk[1])
            test.put("Statistikk", statArr)

            val altArr = JSONArray()
            altArr.put(dilemmaListe[i].alternativ[0])
            altArr.put(dilemmaListe[i].alternativ[1])
            test.put("Alternativer", altArr)

            test.put("SpørsmålsTekst", dilemmaListe[i].tekst)
            test.put("Spørsmål_id", dilemmaListe[i].id)
            DilemmaArray.put(i,test)
        }
        postData.put("Dilemma",DilemmaArray)

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

    fun opprettQuizSpill(author : String, spillNavn: String, quizListe : ArrayList<Quiz>,  callBack: (utført: Boolean?) -> Unit) {
        val postURL = "https://eskapader.herokuapp.com/leggQuiz"
        val requestQueue = newRequestQueue(appContext)

        val postData = JSONObject()

        postData.put("Spillnavn", spillNavn)
        postData.put("Author", author)

        val spørsmålArr = JSONArray();

        for(i in 0 until quizListe.size){
            val spørsmålObjekt = JSONObject()
            spørsmålObjekt.put("Spørsmål_id", quizListe[i].idTall)
            spørsmålObjekt.put("SpørsmålsTekst", quizListe[i].spørsmål)
            spørsmålObjekt.put("Svar", quizListe[i].svarTall)

            val alternativer = JSONArray()
            alternativer.put(quizListe[i].alternativ_Liste[0])
            alternativer.put(quizListe[i].alternativ_Liste[1])
            alternativer.put(quizListe[i].alternativ_Liste[2])
            alternativer.put(quizListe[i].alternativ_Liste[3])
            spørsmålObjekt.put("Alternativer", alternativer)

            spørsmålArr.put(i,spørsmålObjekt)
        }
        postData.put("Spørsmål",  spørsmålArr)

        val req = JsonObjectRequest(Request.Method.POST,postURL,postData,
            {
                    response ->
                val resultat = response.toString(4)
                println(resultat)
                callBack.invoke(true)
            },
            {
                    error ->
                println("Feil oppsto i POST: $error")
                callBack.invoke(false)
            }
        )
        requestQueue.add(req)

    }


    fun oppdaterDilemma(spillnavn : String, dilemmaNr : Int, altNr : Int) {
        val putURL = "https://eskapader.herokuapp.com/oppdaterDilemma"
        val requestQueue = newRequestQueue(appContext)
        val putData = JSONObject()

        putData.put("Spillnavn", spillnavn)
        putData.put("DilemmaNr",dilemmaNr)
        putData.put("AltNr", altNr)


        val req = JsonObjectRequest(Request.Method.PUT,putURL,putData,
            {
                    response ->
                val resultat = response.toString(4)
                println("Oppdaterte dilemma -> ")
                println(resultat)
            },
            {
                    error ->
                println("Feil oppsto i POST: $error")
            }
        )
        requestQueue.add(req)
    }

    fun oppdaterGlobalDilemma(spmNr : Int, valgNr : Int, brukernavn : String){
        val uriParams = "https://eskapader.herokuapp.com/spill/60508baa3948c9ca5972c3b0/${spmNr}/${valgNr}/${brukernavn}"
        val o = JSONObject()
        val requestQueue = newRequestQueue(appContext)
        val req = JsonObjectRequest(Request.Method.PATCH,uriParams,o,
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

    fun hentGlobaleSpillNavn(callBack: (spillListe: ArrayList<String>?) -> Unit) {
        val requestQueue = newRequestQueue(appContext)
        var spillListe : ArrayList<String> = ArrayList()
        val req = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                response ->
                for(i in 0 until response.length()) {
                    var json : JSONObject = response[i] as JSONObject
                    if(json.getString("Spillnavn") == "Musikkquiz")
                    spillListe.add(json.getString("Spillnavn"))
                    if(json.getString("Spillnavn") == "Dilemma")
                        spillListe.add(json.getString("Spillnavn"))
                }
                callBack.invoke(spillListe)

            },
            {
                    error ->
                println("Feil oppsto: $error")

            })


        requestQueue.add(req)

    }

    fun hentMineSpill(author: String ,callBack: (spillListe: ArrayList<String>?) -> Unit) {
        val requestQueue = newRequestQueue(appContext)
        var spillListe : ArrayList<String> = ArrayList()
        val req = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                response ->
                for(i in 2 until response.length()) {
                    val spill : JSONObject = response.getJSONObject(i)
                    if(spill.getString("Author") == author)
                        spillListe.add(spill.getString("Spillnavn"))
                }
                callBack.invoke(spillListe)
            },
            {
                    error ->
                println("Feil oppsto: $error")

            })
        requestQueue.add(req)
    }

    fun slettSpill(spillnavn : String,author: String, spilltype : String,callBack: (velykket: Boolean?) -> Unit) {
        val requestQueue = newRequestQueue(appContext)
        val deleteURL = "https://eskapader.herokuapp.com/SlettSpill"
        val deleteData = JSONObject()
        deleteData.put("Spillnavn", spillnavn)
        deleteData.put("Brukernavn",author)
        deleteData.put("SpillType", spilltype)

        val req = JsonObjectRequest(Request.Method.PUT,deleteURL,deleteData,
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
}

