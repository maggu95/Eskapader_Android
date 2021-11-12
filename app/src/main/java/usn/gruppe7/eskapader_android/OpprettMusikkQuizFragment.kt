package usn.gruppe7.eskapader_android

import android.content.res.ColorStateList
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


class OpprettMusikkQuizFragment : Fragment() {

    lateinit var  textList : ArrayList<EditText>
    lateinit var  radioButtonList : ArrayList<RadioButton>
    lateinit var valgtShape: Drawable
    lateinit var  defaultShape : Drawable
    lateinit var quizListe : ArrayList<Quiz>
    lateinit var brukerRow: TableRow
    private lateinit var kort : CardView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var antQuizSpm = 0;
        valgtShape = context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz) }!!
        defaultShape = context?.let { getDrawable(it,R.drawable.rounded_corner_view) }!!
        radioButtonList = arrayListOf<RadioButton>()
        textList = arrayListOf<EditText>()
        quizListe = arrayListOf<Quiz>()


        val binding = DataBindingUtil.inflate<FragmentOpprettMusikkquizBinding>(inflater,R.layout.fragment_opprett_musikkquiz,container,false)

        

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
                //print("Du trykket på alternativ -> $i")
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

                //val tableRow = TableRow(context)
                //tableRow.setPadding(20, 20, 20, 20)



                val textView = TextView(context)
                textView.setText(quiz.getSpørsmålsTekst())
                textView.setTextColor(Color.BLACK)
                textView.textSize = 25F

                kort = inflater.inflate(R.layout.bruker_row,binding.tabQuiz,false) as CardView
                //kort.requireV
                //test.addView(textView)
               // quizRow.addView(textView)


                val card = CardView(requireContext())
                card.minimumWidth = 900
                card.minimumHeight=  90
                card.setCardBackgroundColor(Color.parseColor("#8A2BE2"))
                card.addView(textView)






                quizRow.addView(card)

                //tableRow.addView(card)
                binding.tabQuiz.addView(quizRow)
                quizRow.setOnClickListener{
                    Toast.makeText(context, "Du trykket på ${quiz.idTall}" , Toast.LENGTH_SHORT).show()
                }
            }
        }



        return binding.root;
    }

    private fun getSvar() : Int {
        for (i in 0 until radioButtonList.size ) {
                if (radioButtonList.get(i).isChecked == true)
                    return i
        }
    return -1
    }




}

