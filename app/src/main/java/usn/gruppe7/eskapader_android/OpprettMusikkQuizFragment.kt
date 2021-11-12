package usn.gruppe7.eskapader_android

import android.app.ActionBar
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUS_UP
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener


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
            if (getSvar() < 0)
                Toast.makeText(context, "Sørg for å velge riktig svar!", Toast.LENGTH_LONG).show()
            else {
                val quiz = Quiz(binding.inputSangtekst.text.toString(), antQuizSpm, getSvar())


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
                    Toast.makeText(context, "Du trykket på ${quiz.idTall}" , Toast.LENGTH_SHORT).show()
                    hentQuiz(quiz.idTall, binding)
                    binding.scrollView.fullScroll(View.FOCUS_UP)
                    var alt = arrayOf(binding.inputAlt1.text.toString(), binding.inputAlt2.text.toString(), binding.inputAlt3.text.toString(), binding.inputAlt4.text.toString())

                    binding.inputSangtekst.addTextChangedListener{
                        binding.btRedigerQuiz.isVisible =
                            !aktivQuiz!!.sammenlign(binding.inputSangtekst.text.toString(), alt)
                    }


                    for (elem: EditText in textList) {
                        elem.addTextChangedListener {
                            alt = arrayOf(binding.inputAlt1.text.toString(), binding.inputAlt2.text.toString(), binding.inputAlt3.text.toString(), binding.inputAlt4.text.toString())
                            binding.btRedigerQuiz.isVisible =
                                !aktivQuiz!!.sammenlign(binding.inputSangtekst.text.toString(), alt)
                        }
                    }
                }
                tømTekst(binding)
            }
        }



        return binding.root;
    }

    private fun hentQuiz(id: Int, binding: FragmentOpprettMusikkquizBinding) {
        binding.inputSangtekst.setText(quizListe.get(id).getSpørsmålsTekst())
        for (i in 0 until textList.size) {
            textList[i].setText(quizListe.get(id).getSpørsmål(i))
        }
    }

    private fun getSvar() : Int {
        for (i in 0 until radioButtonList.size ) {
                if (radioButtonList.get(i).isChecked == true)
                    return i
        }
    return -1
    }

    private fun tømTekst(binding: FragmentOpprettMusikkquizBinding) {
        binding.inputSangtekst.text.clear()
        for (e: EditText in textList)
            e.text.clear()
    }
}

