package usn.gruppe7.eskapader_android


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentMusikkQuizBinding

class MusikkQuizFragment() : Fragment() {
    private lateinit var quizListe : ArrayList<Quiz>
    private var currSpørs : Int = 0
    private var valgtAlternativ : Int? = null
    private var poeng: Int = 0
    lateinit var alternativer : ArrayList<TextView>
    lateinit var valgtShape: Drawable
    lateinit var  defaultShape : Drawable
    lateinit var feilShape : Drawable
    lateinit var korrektShape: Drawable
    var valgtSvar : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizListe = arguments?.getParcelableArrayList<Quiz>("quizListe") as ArrayList<Quiz>
        alternativer = arrayListOf<TextView>()
        valgtShape = context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz) }!!
        defaultShape = context?.let { getDrawable(it,R.drawable.rounded_corner_view) }!!
        feilShape =  context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz_feil) }!!
        korrektShape = context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz_korrekt) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMusikkQuizBinding>(inflater,R.layout.fragment_musikk_quiz,container,false)
        binding.sangtekst.text = quizListe[0].getSpørsmålsTekst()
        binding.musAlt1.text = quizListe[0].getSpørsmål(0)
        binding.musAlt2.text = quizListe[0].getSpørsmål(1)
        binding.musAlt3.text = quizListe[0].getSpørsmål(2)
        binding.musAlt4.text = quizListe[0].getSpørsmål(3)

        alternativer.add(binding.musAlt1)
        alternativer.add(binding.musAlt2)
        alternativer.add(binding.musAlt3)
        alternativer.add(binding.musAlt4)

        for (i in 0 until alternativer.size ) {
            alternativer.get(i).setOnClickListener {
                Log.i("Valgt alternativ: " , "Du trykket på alternativ -> $i")
                alternativer.get(i).background = valgtShape
                valgtAlternativ = i
                for(j in 0 until alternativer.size) {
                    if(alternativer.get(j) != alternativer.get(i) )
                        alternativer.get(j).background = defaultShape
                }
            }
        }




        binding.btBekreft.setOnClickListener {

            if(currSpørs == 4) {
                val quizFerdigFragment = MusikkQuizFerdigFragment.newInstance(poeng)
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.musikkQuiz_Container,quizFerdigFragment)
                fr?.commit()
            }

            if(valgtSvar == true) {
                binding.btBekreft.text = "Bekreft"
                currSpørs++
                valgtAlternativ = null
                binding.musAlt1.background = defaultShape
                binding.musAlt2.background = defaultShape
                binding.musAlt3.background = defaultShape
                binding.musAlt4.background = defaultShape
                binding.sangtekst.text = quizListe[currSpørs].getSpørsmålsTekst()
                binding.musAlt1.text = quizListe[currSpørs].getSpørsmål(0)
                binding.musAlt2.text = quizListe[currSpørs].getSpørsmål(1)
                binding.musAlt3.text = quizListe[currSpørs].getSpørsmål(2)
                binding.musAlt4.text = quizListe[currSpørs].getSpørsmål(3)
                valgtSvar = false
            }
            else {
                if (valgtAlternativ == null) {
                    Toast.makeText(
                        context,
                        "Du må velge et alternativ for å gå videre!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (valgtAlternativ == quizListe[currSpørs].getSvar()) {
                        poeng++
                        alternativer[valgtAlternativ!!].background = korrektShape
                        binding.sangtekst.text = "Riktig svar: \n Poeng: $poeng"
                        for (i in 0 until alternativer.size) {
                            if (i != valgtAlternativ) {
                                alternativer[i].background = feilShape
                            }
                        }
                    } else {
                        binding.sangtekst.text = "Feil svar: \n Poeng: $poeng"
                        for (i in 0 until alternativer.size) {
                            if (i == quizListe[currSpørs].getSvar())
                                alternativer[i].background = korrektShape
                            if (i == valgtAlternativ)
                                alternativer[i].background = valgtShape
                            if (i != valgtAlternativ && i != quizListe[currSpørs].getSvar()) {
                                alternativer[i].background = feilShape
                            }
                        }
                    }
                    valgtSvar = true
                    binding.btBekreft.text = "Neste"
                }





                /*Log.d("Korrekt svar!", "Curr poeng = $poeng")
                */
            }


        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(liste : ArrayList<Quiz>) =
            MusikkQuizFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("quizListe", liste)
                }
            }
    }
}