package usn.gruppe7.eskapader_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match


class DilemmaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Henter listen med dillemma
        val dilemmaListe =  arguments?.getParcelableArrayList<Dilemma>("liste") as ArrayList<Dilemma>
        for (i in 0 until dilemmaListe.size)
            println("FRa fragment ${dilemmaListe.get(i) }")




        return inflater.inflate(R.layout.fragment_dilemma, container, false)
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