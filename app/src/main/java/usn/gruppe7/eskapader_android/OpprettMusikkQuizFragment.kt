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
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.core.widget.addTextChangedListener


class OpprettMusikkQuizFragment : Fragment() {

    lateinit var  textList : ArrayList<EditText>
    lateinit var  radioButtonList : ArrayList<RadioButton>
    lateinit var rowShape: Drawable
    lateinit var quizListe : ArrayList<Quiz>
    //lateinit var  radioGroup: RadioGroup

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
                    //println("ID til valgt objekt -> " + quizListe.indexOf(quiz))
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
                            printEffektiv(quiz)
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

    // TODO: 15/11/2021 Skal fjernes senere
    private fun printEffektiv(quiz: Quiz) {
        println(
            "KLAR FOR Å SLETTES: " + "\n" +
            "Valgt objekt: " + quiz.getSpørsmålsTekst() +
                    "\nID: " + quiz.idTall +
                    "\nIndex i listen:" + quizListe.indexOf(quiz) +
                    "\nStørrelse på listen:" + quizListe.size

        )
    }

    private fun oppdaterId() {
        println("Oppdaterer ID på listen")
        for (i in 0 until quizListe.size) {
            println("GAMMEL id til objekt " + quizListe.get(i).getSpørsmålsTekst() + ": " + quizListe.get(i).getId())
            quizListe.get(i).setId(i)
            println("NY id til objekt " + quizListe.get(i).getSpørsmålsTekst() + ": " + quizListe.get(i).getId())
            println()

        }
    }

    private fun hentQuiz(id: Int, binding: FragmentOpprettMusikkquizBinding) {
        // TODO: 15/11/2021  Bør optimaliseres, bare temporary
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
        volley.opprettQuizSpill("Testbruker",spillnavn+"_Quiz", quizListe) {
            utført ->
            if(utført == true) {
                println("Suksess, ditt spill har blitt lagret")
            }
            else {
                println("Noe gikk galt med oppretting av spill")
            }

        }



    }

}



