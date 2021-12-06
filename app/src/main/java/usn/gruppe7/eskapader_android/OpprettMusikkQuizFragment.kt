package usn.gruppe7.eskapader_android

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController


class OpprettMusikkQuizFragment : Fragment() {

    lateinit var  textList : ArrayList<EditText>
    lateinit var  radioButtonList : ArrayList<RadioButton>
    lateinit var rowShape: Drawable
    lateinit var quizListe : ArrayList<Quiz>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rowShape = context?.let { getDrawable(it,R.drawable.quizrow_drawable) }!!
        var antQuizSpm = 0;
        radioButtonList = arrayListOf<RadioButton>()
        textList = arrayListOf<EditText>()
        quizListe = arrayListOf<Quiz>()
        //radioGroup = RadioGroup(context)

        val binding = DataBindingUtil.inflate<FragmentOpprettMusikkquizBinding>(inflater,R.layout.fragment_opprett_musikkquiz,container,false)
        var aktivQuiz: Quiz? = null

        binding.btRedigerQuiz.isVisible = false

        textList.add(binding.inputAlt1)
        textList.add(binding.inputAlt2)
        textList.add(binding.inputAlt3)
        textList.add(binding.inputAlt4)

        radioButtonList.add(binding.radioButAlt1)
        radioButtonList.add(binding.radioButAlt2)
        radioButtonList.add(binding.radioButAlt3)
        radioButtonList.add(binding.radioButAlt4)

        binding.btSlettInput.setOnClickListener {
            tømTekst(binding)
        }

        binding.btFerdig.setOnClickListener { view : View ->
            if (quizListe.size > 0) {
                sendSpill(requireContext(), binding.inputQuizNavn.text.toString(), quizListe)

                val bundle = Bundle()
                bundle.putString("Spillnavn", binding.inputQuizNavn.text.toString())

                view.findNavController().navigate(R.id.action_opprettMusikkQuizFragment_to_bekreftOpprettetSpill, bundle)

            }
        }

        for (i in 0 until radioButtonList.size ) {
            radioButtonList.get(i).setOnClickListener {
                radioButtonList.get(i).isChecked = true
                for(j in 0 until radioButtonList.size) {
                    if(radioButtonList.get(j) != radioButtonList.get(i) )
                        radioButtonList.get(j).isChecked = false
                }
            }
        }

        binding.btBekreftQuiz.setOnClickListener {

            if (!sjekkInput(binding))
                Toast.makeText(context, "Sørg for at alle feltene er fylt inn!", Toast.LENGTH_SHORT).show()
            else {
                var quiz = Quiz(binding.inputSangtekst.text.toString(), antQuizSpm, getSvar())


                for (i in 0 until textList.size) {
                    quiz.addSpørsmål(textList.get(i).text.toString())
                }

                quizListe.add(quiz)

                val quizRow = QuizRow(quiz, antQuizSpm, context)
                antQuizSpm++

                val textView = TextView(context)
                textView.setText(quiz.getSpørsmålsTekst())
                textView.setTextColor(Color.WHITE)
                textView.textSize = 25F
                textView.setPadding(20, 20, 20, 20)

                textView.maxWidth = binding.tabQuiz.width

                val card = CardView(requireContext())
                card.background = rowShape
                card.addView(textView)

                quizRow.addView(card)
                binding.tabQuiz.addView(quizRow)
                quizRow.setOnClickListener{
                    aktivQuiz = quiz
                    hentQuiz(quizListe.indexOf(quiz), binding)
                    binding.scrollView.fullScroll(View.FOCUS_UP)
                    var alt = arrayOf(binding.inputAlt1.text.toString(), binding.inputAlt2.text.toString(), binding.inputAlt3.text.toString(), binding.inputAlt4.text.toString())

                    binding.inputSangtekst.addTextChangedListener{
                        binding.btRedigerQuiz.isVisible =
                            !aktivQuiz!!.sammenlign(binding.inputSangtekst.text.toString(), alt, getSvar())
                    }


                    for (elem: EditText in textList) {
                        elem.addTextChangedListener {
                            alt = arrayOf(binding.inputAlt1.text.toString(), binding.inputAlt2.text.toString(), binding.inputAlt3.text.toString(), binding.inputAlt4.text.toString())
                            binding.btRedigerQuiz.isVisible =
                                !aktivQuiz!!.sammenlign(binding.inputSangtekst.text.toString(), alt, getSvar())
                        }
                    }


                    for (elem: RadioButton in radioButtonList)
                        elem.setOnCheckedChangeListener { compoundButton, b ->
                            binding.btRedigerQuiz.isVisible =
                                !aktivQuiz!!.sammenlign(binding.inputSangtekst.text.toString(), alt, getSvar())
                        }


                    binding.btRedigerQuiz.setOnClickListener {
                        if (!sjekkInput(binding))
                            Toast.makeText(context, "Sørg for at alle feltene er fylt inn!",
                                           Toast.LENGTH_SHORT).show()
                        else {
                            quiz.setSpørsmålsTekst(binding.inputSangtekst.text.toString())
                            for (elem: EditText in textList) {
                                alt = arrayOf(
                                    binding.inputAlt1.text.toString(),
                                    binding.inputAlt2.text.toString(),
                                    binding.inputAlt3.text.toString(),
                                    binding.inputAlt4.text.toString()
                                )
                                quiz.setAlternativ(alt)
                            }
                            quiz.setSvar(getSvar())
                            textView.setText(quiz.getSpørsmålsTekst())
                            tømTekst(binding)
                            binding.btRedigerQuiz.isVisible = false
                        }
                    }

                    binding.btSlett.setOnClickListener {
                        if (quizListe.indexOf(quiz) < 0)
                            Toast.makeText(context, "Objektet kan ikke slettes!", Toast.LENGTH_SHORT).show()
                        else {
                            val index = quizListe.indexOf(quiz)
                            tømTekst(binding)
                            quizRow.removeAllViews()
                            quizListe.remove(quiz)
                            if (quizListe.size > 0) {
                                oppdaterId()
                            }
                        }
                    }
                }
                tømTekst(binding)
            }
        }
        return binding.root;
    }

    private fun oppdaterId() {
        for (i in 0 until quizListe.size) {
            quizListe.get(i).setId(i)
        }
    }

    private fun hentQuiz(id: Int, binding: FragmentOpprettMusikkquizBinding) {
        for (j in 0 until radioButtonList.size ) {
            radioButtonList.get(j).isChecked = false
        }

        binding.inputSangtekst.setText(quizListe.get(id).getSpørsmålsTekst())
        for (i in 0 until textList.size) {
            textList[i].setText(quizListe.get(id).getSpørsmål(i))
        }
        radioButtonList[quizListe.get(id).getSvar()].isChecked = true
    }

    private fun getSvar() : Int {
        for (i in 0 until radioButtonList.size ) {
                if (radioButtonList.get(i).isChecked)
                    return i
        }
        return -1
    }

    private fun tømTekst(binding: FragmentOpprettMusikkquizBinding) {
        binding.inputSangtekst.text.clear()
        for (e: EditText in textList)
            e.text.clear()
        for (j in 0 until radioButtonList.size ) {
            radioButtonList.get(j).isChecked = false
        }

    }

    private fun sjekkInput(binding: FragmentOpprettMusikkquizBinding) : Boolean {
        if (!sjekkInputString(binding.inputSangtekst.text.toString()))
            return false
        if (getSvar() < 0)
            return false
        for (i in 0 until textList.size) {
            if (!sjekkInputString(textList[i].text.toString()))
                return false
        }
        return true
    }

    private fun sjekkInputString(text: String) : Boolean {
        if (text == "")
            return false
        if (text.trim().isEmpty())
            return false
        return true
    }


    private fun sendSpill(context : Context, spillnavn : String, quizListe : ArrayList<Quiz>) {
        val volley = APIConnector(context)
        val user = app.currentUser()
        val bruker = user?.profile?.email.toString()
        volley.opprettQuizSpill(bruker,spillnavn+"_Quiz", quizListe) {
            utført ->
            if(utført == true) {
                Toast.makeText(context,"Suksess", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context,"Noe gikk galt i oppretting", Toast.LENGTH_SHORT).show()
            }

        }
    }

}



