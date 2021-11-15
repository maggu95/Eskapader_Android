package usn.gruppe7.eskapader_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Dilemma(
    val statistikk : Array<Int>,
    val id: Int,
    val tekst: String,
    val alternativ : Array<String> ) : Parcelable {


    fun getStat1(s: Int): Int {
        return statistikk[0]
    }

    fun getStatistikkArr() : Array<Int> {
        return statistikk
    }
    fun getAlternativArr() : Array<String> {
        return alternativ
    }

    fun getStat2(s: Int): Int {
        return statistikk[1]
    }


    fun getDilemmaTekst(): String {
        return tekst
    }

    override fun toString(): String {
        return "$id  $tekst  ${alternativ} ${statistikk }"
    }

}

