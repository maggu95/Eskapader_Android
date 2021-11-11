package usn.gruppe7.eskapader_android

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding
import androidx.appcompat.content.res.AppCompatResources.getDrawable


class OpprettMusikkQuizFragment : Fragment() {

    lateinit var  textList : ArrayList<EditText>
    lateinit var  radioButtonList : ArrayList<RadioButton>
    lateinit var valgtShape: Drawable
    lateinit var  defaultShape : Drawable
    lateinit var quizListe : ArrayList<Quiz>
    lateinit var brukerRow: TableRow

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
                val quiz = Quiz(binding.inputSangtekst.text.toString(), 0, getSvar())
                for (i in 0 until textList.size) {
                    quiz.addSpørsmål(textList.get(i).text.toString())
                }

                quizListe.add(quiz)

                val quizRow = QuizRow(quiz, antQuizSpm)
                quizRow.addRow(R.layout.bruker_row)

                val textView = TextView(context)
                textView.setText(quiz.getSpørsmålsTekst())
                val tableRow = TableRow(context)
                tableRow.setPadding(20, 20, 20, 20)
                //tableRow.addView(quizRow)

                binding.tabQuiz.addView(tableRow)
                tableRow.setOnClickListener{
                    Toast.makeText(context, "You hit the quan!: ${binding.tabQuiz}" , Toast.LENGTH_LONG).show()
                }
            }
        }




        /*
        for (i in 0 until textList.size ) {
            textList.get(i).setOnClickListener {
                print("Du trykket på alternativ -> $i")
                textList.get(i).setBackgroundDrawable(valgtShape)
                textList.get(i).background = valgtShape
                //valgtAlternativ = i
                for(j in 0 until textList.size) {
                    if(textList.get(j) != textList.get(i) )
                        textList.get(j).background = defaultShape
                }
            }
        }

         */

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

