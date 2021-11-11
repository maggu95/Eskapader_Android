package usn.gruppe7.eskapader_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Dilemma(
    val statistikk : Array<Int>,
    val id: Int,
    val tekst: String,
    val alternativ : Array<String> ) : Parcelable {


    fun getStat1(): Int {
        return statistikk[0]
    }

    fun getStat2(): Int {
        return statistikk[1]
    }


    fun getDilemmaTekst(): String {
        return tekst
    }

    override fun toString(): String {
        return "$id  $tekst  ${alternativ} ${statistikk }"
    }

}

