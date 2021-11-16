package usn.gruppe7.eskapader_android

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
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
    private val type_liste = mutableListOf<String>()
    private lateinit var btLeggTilQuiz: View
    private var spillListe : ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHovedMenyBinding>(inflater,R.layout.fragment_hoved_meny,container,false)
        val recyclerAdapter = binding.spillListe
        recyclerAdapter.layoutManager = LinearLayoutManager(context)
        recyclerAdapter.adapter = context?.let { RecyclerAdapter(it,tittelListe,instruks1_Liste,instruks2_Liste,instruks3_Liste,bildeliste,type_liste) }



        val sharedPreference = activity?.getSharedPreferences("SPILL_LISTE", Context.MODE_PRIVATE)
        val arr = sharedPreference?.getStringSet("Arr", null)


        if (arr != null) {
            spillListe = arr.toMutableList() as ArrayList<String>
        }
        else {
            println("Arr er null")
        }

        if (spillListe != null) {
            for (i in 0 until spillListe.size) {
                println("Legger til som kort -> ${spillListe[i]}" )
                if(spillListe[i].contains("Dilemma",true))
                    leggTilSpill(spillListe[i] , "asd", "asd", "asd", R.mipmap.ic_launcher_round, "Dilemma" )
                if(spillListe[i].contains("Quiz",true))
                    leggTilSpill(spillListe[i] , "asd", "asd", "asd", R.mipmap.ic_launcher_round, "Quiz" )
            }


        }
        else {
            println("Spill liste er null")
            fyllEksempelData()
        }



        btLeggTilQuiz = binding.btLeggTilQuiz
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


    fun leggTilSpill(tittel: String, instruks1: String,instruks2: String,instruks3: String, bilde: Int, type : String ) {
        tittelListe.add(tittel)
        instruks1_Liste.add(instruks1)
        instruks2_Liste.add(instruks2)
        instruks3_Liste.add(instruks3)
        bildeliste.add(bilde)
        type_liste.add(type)
    }

    fun fyllEksempelData() {
        leggTilSpill("Musikkquiz", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round, "Dilemma")
        leggTilSpill("Dilemma", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round, "Dilemma")
    }





}