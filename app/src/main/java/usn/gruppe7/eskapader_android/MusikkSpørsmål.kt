package usn.gruppe7.eskapader_android

class MusikkSpørsmål (spørsmål: String, idTall: Int) {
    private val spørsmålsTekst = spørsmål
    private val id =  idTall;
    var spørsmål : MutableList<String> = mutableListOf()




    fun addSpørsmål (s : String) {
        spørsmål.add(s)
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
                "Spørsmål 4 -> " + spørsmål[3]


    }












}