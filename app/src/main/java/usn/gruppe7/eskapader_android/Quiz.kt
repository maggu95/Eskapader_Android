package usn.gruppe7.eskapader_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Quiz (val spørsmål: String, val idTall: Int, val svarTall: Int) : Parcelable {
    private val spørsmålsTekst = spørsmål
    private val id =  idTall
    private val svar = svarTall
    var alternativ_Liste : MutableList<String> = mutableListOf()



    fun addSpørsmål (s : String) {
        alternativ_Liste.add(s)
    }

    fun getSvar (): Int {
        return svar;
    }

    fun getSpørsmålsTekst() : String {
        return spørsmålsTekst
    }

    fun getSpørsmål(i : Int): String {
        return alternativ_Liste[i]
    }

    fun sammenlign(tittel: String, alt : Array<String>) : Boolean {
        if (spørsmålsTekst != tittel) {
            println(spørsmålsTekst + " ---- " + tittel)
            println("Spørsmålstekst stemmer ikke")
            return false;
        }
        for (i in 0 until alt.size) {
            if (alt[i] != alternativ_Liste[i]) {
                println("Alternativer stemmer ikke")
                return false;
            }
        }
        return true;
    }

    override fun toString(): String {
        return "\n" +"Spørsmålstekst -> " + spørsmålsTekst + "\n" +
                "Id -> " + id + "\n" +
                "Spørsmål 1 -> " + alternativ_Liste[0] + "\n" +
                "Spørsmål 2 -> " + alternativ_Liste[1] + "\n" +
                "Spørsmål 3 -> " + alternativ_Liste[2] + "\n" +
                "Spørsmål 4 -> " + alternativ_Liste[3] + "\n" +
                "Svar -> " + getSvar()


    }

}