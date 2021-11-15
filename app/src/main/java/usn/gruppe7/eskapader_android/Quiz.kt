package usn.gruppe7.eskapader_android

import android.os.Parcelable
import android.text.Editable
import kotlinx.parcelize.Parcelize


@Parcelize
class Quiz (val spørsmål: String, val idTall: Int, val svarTall: Int) : Parcelable {
    private var spørsmålsTekst = spørsmål
    private var id =  idTall
    private var svar = svarTall
    var alternativ_Liste : MutableList<String> = mutableListOf()

    fun getId() : Int {
        return this.id
    }

    fun setId(nyId: Int) {
        this.id = nyId
    }

    fun setAlternativ(alt : Array<String>) {
        for (i in 0 until alternativ_Liste.size)
            alternativ_Liste[i] = alt[i]
    }

    fun getSvar (): Int {
        return svar;
    }

    fun setSvar(nySvarTall: Int) {
        this.svar = nySvarTall
    }

    fun setSpørsmålsTekst(tekst: String) {
        this.spørsmålsTekst = tekst.toString()
    }

    fun getSpørsmålsTekst() : String {
        return spørsmålsTekst
    }

    fun addSpørsmål (s : String) {
        alternativ_Liste.add(s)
    }

    fun getSpørsmål(i : Int): String {
        return alternativ_Liste[i]
    }

    fun sammenlign(tittel: String, alt : Array<String>, svarTall: Int) : Boolean {
        if (spørsmålsTekst != tittel) {
            //println(spørsmålsTekst + " ---- " + tittel)
            //println("Spørsmålstekst stemmer ikke")
            return false
        }
        for (i in 0 until alt.size) {
            if (alt[i] != alternativ_Liste[i]) {
                //println("Alternativer stemmer ikke")
                return false
            }
        }

        if (svar != svarTall)
            return false

        return true;
    }

    override fun toString(): String {
        return "\n" +"Spørsmålstekst -> " + spørsmålsTekst + "\n" +
                "Id -> " + id + "\n" +
                "Spørsmål 1 -> " + alternativ_Liste[0] + ", " +
                "Spørsmål 2 -> " + alternativ_Liste[1] + ", " +
                "Spørsmål 3 -> " + alternativ_Liste[2] + ", " +
                "Spørsmål 4 -> " + alternativ_Liste[3] + "\n" +
                "Svar -> " + getSvar()


    }

}