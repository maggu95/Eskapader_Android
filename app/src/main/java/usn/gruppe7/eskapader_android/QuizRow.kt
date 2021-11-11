package usn.gruppe7.eskapader_android

import android.text.Layout
import android.widget.TableRow

class QuizRow(val quiz: Quiz, val nr: Int) {

    var row: Int? = null


    fun addRow(tableRow: Int) {
        row = tableRow
    }

}