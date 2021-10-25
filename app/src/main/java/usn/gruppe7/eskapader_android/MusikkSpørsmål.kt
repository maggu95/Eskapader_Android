package usn.gruppe7.eskapader_android

import android.os.Parcel
import android.os.Parcelable

class MusikkSpørsmål (spørsmål: String, idTall: Int, svarTall: Int) : Parcelable {
    private val spørsmålsTekst = spørsmål
    private val id =  idTall
    private val svar = svarTall
    var spørsmål : MutableList<String> = mutableListOf()

    constructor(parcel: Parcel) : this (
        TODO("spørsmål"),
        TODO("idTall"),
        TODO("svar")
    )


    fun addSpørsmål (s : String) {
        spørsmål.add(s)
    }

    fun getSvar (): Int {
        return svar;
    }

    fun getSpørsmålsTekst() : String {
        return spørsmålsTekst
    }

    fun getSpørsmål(i : Int): String {
        return spørsmål[i]
    }



    override fun toString(): String {
        return "\n" +"Spørsmålstekst -> " + spørsmålsTekst + "\n" +
                "Id -> " + id + "\n" +
                "Spørsmål 1 -> " + spørsmål[0] + "\n" +
                "Spørsmål 2 -> " + spørsmål[1] + "\n" +
                "Spørsmål 3 -> " + spørsmål[2] + "\n" +
                "Spørsmål 4 -> " + spørsmål[3] + "\n" +
                "Svar -> " + getSvar()


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusikkSpørsmål> {
        override fun createFromParcel(parcel: Parcel): MusikkSpørsmål {
            return MusikkSpørsmål(parcel)
        }

        override fun newArray(size: Int): Array<MusikkSpørsmål?> {
            return arrayOfNulls(size)
        }
    }


}