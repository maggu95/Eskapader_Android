package usn.gruppe7.eskapader_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Dilemma(
    val statistikk1: Int,
    val statistikk2: Int,
    val id: Int,
    val tekst: String,
    val alternativ1: String,
    val alternativ2: String ) : Parcelable {


    fun getStat1(): Int {
        return statistikk1
    }

    fun getStat2(): Int {
        return statistikk2
    }


    fun getDilemmaTekst(): String {
        return tekst
    }

    override fun toString(): String {
        return "$id \n $tekst \n $alternativ1  $alternativ2"
    }

}

