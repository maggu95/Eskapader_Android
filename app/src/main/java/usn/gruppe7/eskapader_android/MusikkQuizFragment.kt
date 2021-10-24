package usn.gruppe7.eskapader_android


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentMusikkQuizBinding

class MusikkQuizFragment() : Fragment() {
    private lateinit var quizListe : ArrayList<MusikkSpørsmål>
    private var currSpørs : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizListe = arguments?.getParcelableArrayList<MusikkSpørsmål>("quizListe") as ArrayList<MusikkSpørsmål>
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


        binding.btBekreft.setOnClickListener {
            currSpørs++
            binding.sangtekst.text = quizListe[currSpørs].getSpørsmålsTekst()
            binding.musAlt1.text = quizListe[currSpørs].getSpørsmål(0)
            binding.musAlt2.text = quizListe[currSpørs].getSpørsmål(1)
            binding.musAlt3.text = quizListe[currSpørs].getSpørsmål(2)
            binding.musAlt4.text = quizListe[currSpørs].getSpørsmål(3)
        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(liste : ArrayList<MusikkSpørsmål>) =
            MusikkQuizFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("quizListe", liste)
                }
            }
    }
}