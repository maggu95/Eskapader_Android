package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentMusikkQuizFerdigBinding


class MusikkQuizFerdigFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val poeng = arguments?.getInt("poeng")
        val binding = DataBindingUtil.inflate<FragmentMusikkQuizFerdigBinding>(inflater,R.layout.fragment_musikk_quiz_ferdig,container,false)
        binding.resultatTxt.text = "Poengsum: \n $poeng"
        binding.backBtn.setOnClickListener {
            val intent = Intent(context, HovedMenyActivity::class.java)
            startActivity(intent)

        }



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