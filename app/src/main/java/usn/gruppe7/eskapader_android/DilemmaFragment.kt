package usn.gruppe7.eskapader_android

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentDilemmaBinding

// TODO: Rename parameter arguments, choose names that match


class DilemmaFragment : Fragment() {

    lateinit var  normalShape : Drawable
    lateinit var korrektShape: Drawable
    private var valgtDilemma : Boolean = true
    private var valgtAlternativ: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // lager binding
        val binding = DataBindingUtil.inflate<FragmentDilemmaBinding>(inflater,R.layout.fragment_dilemma,container,false)
        var currDilemma : Int = 0;


        //Shapes
        normalShape = context?.let { getDrawable(it,R.drawable.rounded_borders_dilemma2) }!!
        korrektShape = context?.let { getDrawable(it,R.drawable.rounded_borders_dilemma) }!!


        //Henter listen med dillemma
        val dilemmaListe =  arguments?.getParcelableArrayList<Dilemma>("liste") as ArrayList<Dilemma>

        binding.dillAlt1.text = dilemmaListe.get(currDilemma).alternativ[0]
        binding.dillAlt2.text = dilemmaListe.get(currDilemma).alternativ[1]

        val volley = APIConnector (requireContext())

        //Du valgte alternativ 1
        binding.dillAlt1.setOnClickListener{
            if (!valgtDilemma)
                binding.dillAlt1.background = normalShape
            else
                binding.dillAlt1.background = korrektShape
                binding.btNesteDilemma.visibility = View.VISIBLE
                valgtDilemma = true
                valgtAlternativ = 0
        }

        //Du valgte alternativ 2
        binding.dillAlt2.setOnClickListener{
            if (!valgtDilemma)
                binding.dillAlt2.background = normalShape
            else
                binding.dillAlt2.background = korrektShape
            binding.btNesteDilemma.visibility = View.VISIBLE
            valgtDilemma = true
            valgtAlternativ = 1


        }

        //Neste dilemma btn
        binding.btNesteDilemma.setOnClickListener {

            if(currDilemma == 4) {
                val dilemmaFerdigFragment = DilemmaFerdigFragment.newInstance(stats = "")
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.dilemma_Container, dilemmaFerdigFragment)
                fr?.commit()
            }

            if (valgtDilemma == true) {
                binding.btNesteDilemma.text = "Neste"
                currDilemma++
                binding.dillAlt1.text = dilemmaListe[currDilemma].alternativ[0]
                binding.dillAlt2.text = dilemmaListe[currDilemma].alternativ[1]
                binding.dillAlt1.background = normalShape
                binding.dillAlt2.background = normalShape
                binding.btNesteDilemma.visibility = View.INVISIBLE
            }
        }


        // Returnerer binding for view
        return binding.root
    }

    companion object {
        fun newInstance(dilemmaListe : ArrayList<Dilemma>) =
            DilemmaFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("liste", dilemmaListe)
                }
            }
    }
}

