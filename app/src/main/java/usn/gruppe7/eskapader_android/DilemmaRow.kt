package usn.gruppe7.eskapader_android

import android.content.Context
import android.text.Layout
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.cardview.widget.CardView

class DilemmaRow(val dilemma: Dilemma, val nr: Int, context: Context?) : TableRow(context) {
    var kort : CardView? = null


    fun addKort( k : CardView) {
        kort = k
        //addView(txt,0)
        addView(kort)
    }




}