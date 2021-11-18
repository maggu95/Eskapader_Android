package usn.gruppe7.eskapader_android

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettDilemmaBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding

class OpprettDilemmaFragment : Fragment() {

    lateinit var dilemmaListe : ArrayList<Dilemma>
    lateinit var rowShape: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rowShape = context?.let { AppCompatResources.getDrawable(it, R.drawable.quizrow_drawable) }!!

        val binding = DataBindingUtil.inflate<FragmentOpprettDilemmaBinding>(inflater,R.layout.fragment_opprett_dilemma,container,false)
        val volley = context?.let { APIConnector(it) }
        dilemmaListe = arrayListOf<Dilemma>()

        var antDilemma = 0

        //volley.opprettDIlemmaSpill(author,spillnavn,dilemmaListe);



        //val textList = ArrayList<EditText>()


        binding.btLeggtilDilemma.setOnClickListener {

            val spillnavn = binding.inputDilemmaSpillnavn.text.toString()
            var id = dilemmaListe.size
            val statListe = arrayOf(0,0)
            var alternativListe = arrayOf(binding.inputDilemmaAlt1.text.toString(), binding.inputDilemmaAlt2.text.toString())

            var dilemma = Dilemma(statListe, id, spillnavn, alternativListe)
            dilemmaListe.add(dilemma)

            val quizRow = DilemmaRow(dilemma, antDilemma, context)
            antDilemma++

            val textView = TextView(context)
            textView.setText(antDilemma.toString())
            textView.setTextColor(Color.WHITE)
            textView.textSize = 25F
            textView.setPadding(20, 20, 20, 20)

            textView.maxWidth = binding.tabQuiz.width

            val card = CardView(requireContext())
            card.background = rowShape
            card.addView(textView)

            quizRow.addView(card)
            binding.tabQuiz.addView(quizRow)

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