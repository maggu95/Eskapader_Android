package usn.gruppe7.eskapader_android

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class VolleyObjekt(val appContext: Context) : Volley() {



    fun hentSpill(url: String) {
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






}