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


class DilemmaFerdigFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val stats = arguments?.getDouble("Stats") as Double
        val listeOver50 = listOf<String>("Du er som folk flest!", "Du er ikke unik", "Du følger flokken!")
        val listeUnder50 = listOf<String>("Du er unik, og du velger ikke som alle andre",
            "Her har du laget din egen sti, og den er du fornøyd med", "Du er mester av din egen skjebne, og fri som en fugl")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDilemmaFerdigBinding>(inflater,R.layout.fragment_dilemma_ferdig,container,false)
        binding.resultatTxt.text = "Du var enig med flertallet ${Math.round(stats)} % av tiden!"

        if (stats >= 50) {
            val random = (0..listeOver50.size-1).random()
            binding.generertTxt.text = listeOver50[random]
        }
        else {
            val random = (0..listeUnder50.size-1).random()
            binding.generertTxt.text = listeUnder50[random]
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(context, HovedMenyActivity::class.java)
            startActivity(intent)

        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(stats: Double) =
            DilemmaFerdigFragment().apply {
                arguments = Bundle().apply {
                    putDouble("Stats", stats)
                }
            }
    }
}