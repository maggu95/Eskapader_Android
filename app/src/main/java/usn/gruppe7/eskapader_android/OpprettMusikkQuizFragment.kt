package usn.gruppe7.eskapader_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettMusikkquizBinding
import android.widget.EditText




class OpprettMusikkQuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentOpprettMusikkquizBinding>(inflater,R.layout.fragment_opprett_musikkquiz,container,false)

        val textList = ArrayList<EditText>()
        textList.add(binding.inputAlt1)
        textList.add(binding.inputAlt2)
        textList.add(binding.inputAlt3)
        textList.add(binding.inputAlt4)

        binding.btBekreftQuiz.setOnClickListener {
            for (item in textList) {
                item.text.clear()
            }
        }


        return binding.root;
    }





}