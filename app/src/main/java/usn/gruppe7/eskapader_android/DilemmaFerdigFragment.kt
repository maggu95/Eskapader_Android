package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentDilemmaFerdigBinding
import usn.gruppe7.eskapader_android.databinding.FragmentMusikkQuizFerdigBinding


class DilemmaFerdigFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val stats = arguments?.getString("stats")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDilemmaFerdigBinding>(inflater,R.layout.fragment_dilemma_ferdig,container,false)
        binding.backBtn.setOnClickListener {
            val intent = Intent(context, HovedMenyActivity::class.java)
            startActivity(intent)

        }



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(stats : String) =
            DilemmaFerdigFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}