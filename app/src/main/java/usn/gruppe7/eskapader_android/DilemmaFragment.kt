package usn.gruppe7.eskapader_android

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentDilemmaBinding

// TODO: Rename parameter arguments, choose names that match


class DilemmaFragment : Fragment() {

    lateinit var valgtShape: Drawable
    lateinit var  defaultShape : Drawable
    lateinit var feilShape : Drawable
    lateinit var korrektShape: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // lager binding
        val binding = DataBindingUtil.inflate<FragmentDilemmaBinding>(inflater,R.layout.fragment_dilemma,container,false)

        var currDilemma : Int = 0;


        //Shapes
        valgtShape = context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz) }!!
        defaultShape = context?.let { getDrawable(it,R.drawable.rounded_corner_view) }!!
        feilShape =  context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz_feil) }!!
        korrektShape = context?.let { getDrawable(it,R.drawable.rounded_borders_musikkquiz_korrekt) }!!

        //Henter listen med dillemma
        val dilemmaListe =  arguments?.getParcelableArrayList<Dilemma>("liste") as ArrayList<Dilemma>

        binding.dillAlt1.text = dilemmaListe.get(currDilemma).alternativ[0]
        binding.dillAlt2.text = dilemmaListe.get(currDilemma).alternativ[1]




        //Du valgte alternativ 1
        binding.dillAlt1.setOnClickListener{

        }

        //Du valgte alternativ 2
        binding.dillAlt2.setOnClickListener{

        }

        //Neste dilemma btn
        binding.btNesteDilemma.setOnClickListener {

        }




        // Returnerer binding for view
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(dilemmaListe : ArrayList<Dilemma>) =
            DilemmaFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("liste", dilemmaListe)
                }
            }
    }
}