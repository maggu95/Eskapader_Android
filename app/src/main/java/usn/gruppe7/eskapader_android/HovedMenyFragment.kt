package usn.gruppe7.eskapader_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding

class HovedMenyFragment : Fragment() {
    private val tittelListe = mutableListOf<String>()
    private val instruks1_Liste = mutableListOf<String>()
    private val instruks2_Liste = mutableListOf<String>()
    private val instruks3_Liste = mutableListOf<String>()
    private val bildeliste = mutableListOf<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHovedMenyBinding>(inflater,R.layout.fragment_hoved_meny,container,false)
        val recyclerAdapter = binding.spillListe
        recyclerAdapter.layoutManager = LinearLayoutManager(context)
        recyclerAdapter.adapter = RecyclerAdapter(tittelListe,instruks1_Liste,instruks2_Liste,instruks3_Liste,bildeliste)
        fyllEksempelData()
        return binding.root
    }


    fun leggTilSpill(tittel: String, instruks1: String,instruks2: String,instruks3: String, bilde: Int ) {
        tittelListe.add(tittel)
        instruks1_Liste.add(instruks1)
        instruks2_Liste.add(instruks2)
        instruks3_Liste.add(instruks3)
        bildeliste.add(bilde)
    }

    fun fyllEksempelData() {
        for(i in 1..25) {
            if (i == 1) {
                leggTilSpill("Musikkquiz", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round)
            }
            if (i == 2) {
                leggTilSpill("Dilemma", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round)
            }

            //leggTilSpill("Spill $i", "Instuks 1", "Instruks 2", "Instruks 3", R.mipmap.ic_launcher_round)
        }
    }





}