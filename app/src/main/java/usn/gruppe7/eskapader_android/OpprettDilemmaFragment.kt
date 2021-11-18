package usn.gruppe7.eskapader_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettDilemmaBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding

class OpprettDilemmaFragment : Fragment() {

    lateinit var dilemmaListe : ArrayList<Dilemma>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentOpprettDilemmaBinding>(inflater,R.layout.fragment_opprett_dilemma,container,false)
        val volley = context?.let { APIConnector(it) }
        dilemmaListe = arrayListOf<Dilemma>()

        //volley.opprettDIlemmaSpill(author,spillnavn,dilemmaListe);



        //val textList = ArrayList<EditText>()


        binding.btLeggtilDilemma.setOnClickListener {
            val spillnavn = binding.inputDilemmaSpillnavn.text.toString()
            var id = dilemmaListe.size
            val statListe = arrayOf(0,0)
            var alternativListe = arrayOf(binding.inputDilemmaAlt1.text.toString(), binding.inputDilemmaAlt2.text.toString())

            var dilemma = Dilemma(statListe, id, spillnavn, alternativListe)
            dilemmaListe.add(dilemma)

            tømInput(binding)

            println(dilemmaListe.toString())
        }

    return binding.root;
    }

    fun tømInput(binding: FragmentOpprettDilemmaBinding ) {
        //binding.inputDilemmaSpillnavn.text.clear()
        binding.inputDilemmaAlt1.text.clear()
        binding.inputDilemmaAlt2.text.clear()
    }
}