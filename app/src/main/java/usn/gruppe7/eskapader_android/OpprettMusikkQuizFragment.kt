package usn.gruppe7.eskapader_android

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable


class OpprettMusikkQuizFragment : Fragment() {

    lateinit var  textList : ArrayList<EditText>
    lateinit var valgtShape: Drawable
    lateinit var  defaultShape : Drawable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        valgtShape = context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz) }!!
        defaultShape = context?.let { getDrawable(it,R.drawable.rounded_corner_view) }!!
        textList = arrayListOf<EditText>()

        val binding = DataBindingUtil.inflate<FragmentOpprettMusikkquizBinding>(inflater,R.layout.fragment_opprett_musikkquiz,container,false)


        textList.add(binding.inputAlt1)
        textList.add(binding.inputAlt2)
        textList.add(binding.inputAlt3)
        textList.add(binding.inputAlt4)

        //val textbox = binding.txtVisAlt

        binding.btBekreftQuiz.setOnClickListener {

            for (item in textList) {
                //item.setFoc
            }
        }

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

        return binding.root;
    }





}

