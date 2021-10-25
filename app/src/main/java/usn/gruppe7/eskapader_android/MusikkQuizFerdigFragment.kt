package usn.gruppe7.eskapader_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentMusikkQuizFerdigBinding
import usn.gruppe7.eskapader_android.databinding.MusikkquizSpillBinding


class MusikkQuizFerdigFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val poeng = arguments?.getInt("poeng")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMusikkQuizFerdigBinding>(inflater,R.layout.fragment_musikk_quiz_ferdig,container,false)
        //return inflater.inflate(R.layout.fragment_musikk_quiz_ferdig, container, false)
        binding.resultatTxt.text = "Poengsum: \n $poeng"
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(poeng : Int) =
            MusikkQuizFerdigFragment().apply {
                arguments = Bundle().apply {

                    putInt("poeng", poeng)
                }
            }
    }
}