package usn.gruppe7.eskapader_android

import android.os.Bundle
import android.util.Log.INFO
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import usn.gruppe7.eskapader_android.databinding.FragmentHovedMenyBinding

class HovedMenyFragment : Fragment() {
    private val tittelListe = mutableListOf<String>()
    private val instruks1_Liste = mutableListOf<String>()
    private val instruks2_Liste = mutableListOf<String>()
    private val instruks3_Liste = mutableListOf<String>()
    private val bildeliste = mutableListOf<Int>()
    private lateinit var btLeggTilQuiz: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHovedMenyBinding>(inflater,R.layout.fragment_hoved_meny,container,false)
        val recyclerAdapter = binding.spillListe
        recyclerAdapter.layoutManager = LinearLayoutManager(context)
        recyclerAdapter.adapter = RecyclerAdapter(tittelListe,instruks1_Liste,instruks2_Liste,instruks3_Liste,bildeliste)
        fyllEksempelData()

        //btLeggTilQuiz = findViewById(R.id.btLeggTilQuiz)
        btLeggTilQuiz = binding.btLeggTilQuiz;
        btLeggTilQuiz.setOnClickListener { view : View ->
            val popupMenu: PopupMenu = PopupMenu(context, btLeggTilQuiz)
            popupMenu.menuInflater.inflate(R.menu.item_ny_quiz, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener( PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_musikkquiz ->
                        view.findNavController().navigate(R.id.action_hovedMenyFragment_to_opprettMusikkQuizFragment)
                    R.id.item_dilemma ->
                        Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            })
            popupMenu.show()
        }

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
        for(i in 1..25) {}
        leggTilSpill("Musikkquiz", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round)
        leggTilSpill("Dilemma", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round)

    }





}