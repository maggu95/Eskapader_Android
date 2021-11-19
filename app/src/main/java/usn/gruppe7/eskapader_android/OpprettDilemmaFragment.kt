package usn.gruppe7.eskapader_android

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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

        var aktivDilemma: Dilemma? = null


        val spillnavn = binding.inputDilemmaSpillnavn.text.toString()


        //val textList = ArrayList<EditText>()

        binding.btFerdig.setOnClickListener { view : View ->
            if (dilemmaListe.size > 0) {
                sendSpill(requireContext(), binding.inputDilemmaSpillnavn.text.toString(), dilemmaListe)

                val bundle = Bundle()
                bundle.putString("Spillnavn", binding.inputDilemmaSpillnavn.text.toString())

                view.findNavController().navigate(R.id.action_opprettDilemmaFragment_to_bekreftOpprettetSpill, bundle)

                /*
                val ferdigFragment = BekreftOpprettetSpill.newInstance(binding.inputQuizNavn.text.toString())
                val fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.opprettMusikkquiz_container, ferdigFragment)
                fr?.commit()

                 */
            }
        }


        binding.btLeggtilDilemma.setOnClickListener {
            if (!sjekkInput(binding))
                Toast.makeText(context, "Du må fylle inn alle feltene!", Toast.LENGTH_SHORT).show()
            else {
                var id = dilemmaListe.size
                val statListe = arrayOf(0, 0)
                var alternativListe = arrayOf(
                    binding.inputDilemmaAlt1.text.toString(),
                    binding.inputDilemmaAlt2.text.toString()
                )

                var dilemma = Dilemma(statListe, id, spillnavn, alternativListe)
                dilemmaListe.add(dilemma)

                val dilemmaRow = DilemmaRow(dilemma, antDilemma, context)
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

                dilemmaRow.addView(card)
                binding.tabQuiz.addView(dilemmaRow)

                dilemmaRow.setOnClickListener {
                    aktivDilemma = dilemma
                    binding.scrollView.fullScroll(View.FOCUS_UP)
                    hentDilemma(dilemmaListe.indexOf(dilemma), binding)

                    binding.btDilemmaSlett.setOnClickListener {
                        if (dilemmaListe.indexOf(dilemma) < 0)
                            Toast.makeText(
                                context,
                                "Objektet kan ikke slettes!",
                                Toast.LENGTH_SHORT
                            ).show()
                        else {
                            val index = dilemmaListe.indexOf(dilemma)
                            tømInput(binding)
                            dilemmaRow.removeAllViews()
                            dilemmaListe.remove(dilemma)
                            if (dilemmaListe.size > 0) {
                                oppdaterId()
                            }
                        }
                    }
                }
                tømInput(binding)
            }
        }


    return binding.root;
    }

    private fun sjekkInput(binding: FragmentOpprettDilemmaBinding) : Boolean {
        if (binding.inputDilemmaAlt1.text.toString() == "" || binding.inputDilemmaAlt2.text.toString() == "" )
            return false
        if (binding.inputDilemmaAlt1.text.toString().trim().isEmpty() || binding.inputDilemmaAlt2.text.toString().trim().isEmpty())
            return false
        return true
    }

    private fun hentDilemma(id: Int, binding: FragmentOpprettDilemmaBinding) {
        val altArray: Array<String> = dilemmaListe.get(id).getAlternativArr()
        binding.inputDilemmaAlt1.setText(altArray[0].toString())
        binding.inputDilemmaAlt2.setText(altArray[1].toString())
    }

    fun tømInput(binding: FragmentOpprettDilemmaBinding ) {
        //binding.inputDilemmaSpillnavn.text.clear()
        binding.inputDilemmaAlt1.text.clear()
        binding.inputDilemmaAlt2.text.clear()
    }

    private fun oppdaterId() {
        println("Oppdaterer ID på listen")
        for (i in 0 until dilemmaListe.size) {
            dilemmaListe.get(i).id = id
        }
    }

    private fun sendSpill(context : Context, spillnavn : String, dilemmaListe: ArrayList<Dilemma>) {
        val volley = APIConnector(context)
        val user = app.currentUser()
        val author = user?.profile?.email.toString()

        volley.opprettDIlemmaSpill(author ,spillnavn+"_Dilemma", dilemmaListe)
    }
}