package usn.gruppe7.eskapader_android

import android.content.Context
import android.os.Bundle
import android.util.Log
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
    private var spillArray : ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        println("Du er nå på HovedmenyFragment")

        // Inflate the layout for this fragment
        fjernKort()
        val binding = DataBindingUtil.inflate<FragmentHovedMenyBinding>(inflater,R.layout.fragment_hoved_meny,container,false)
        val recyclerAdapter = binding.spillListe
        recyclerAdapter.layoutManager = LinearLayoutManager(context)
        recyclerAdapter.adapter = context?.let { RecyclerAdapter(it,tittelListe,instruks1_Liste,instruks2_Liste,instruks3_Liste,bildeliste,type_liste) }



        val sharedPreference = activity?.getSharedPreferences("SPILL_LISTE", Context.MODE_PRIVATE)
        val arr = sharedPreference?.getStringSet("Arr", null)

        binding.swiperefresh.setOnRefreshListener {
            val user = app.currentUser()
            if (user != null) {
                if (user.profile.email != null) {

                    val volley = context?.let { APIConnector(it) }
                    volley?.hentAlleSpill { result ->
                        if (result != null) {
                            fjernKort()
                            for (i in 0 until result.size) {
                                if (result[i].contains("Dilemma", true)) {
                                    if (!tittelListe.contains(result[i])) {
                                        leggTilSpill(
                                            result[i],
                                            "asd",
                                            "asd",
                                            "asd",
                                            R.mipmap.icon_dilemma,
                                            "Dilemma"
                                        )
                                        println("${result[i]} gikk gjennomm")
                                    }
                                }
                                if (result[i].contains("Quiz", true)) {
                                    if (!tittelListe.contains(result[i])) {
                                        leggTilSpill(
                                            result[i],
                                            "asd",
                                            "asd",
                                            "asd",
                                            R.mipmap.icon_music,
                                            "Quiz"
                                        )
                                        println("${result[i]} gikk gjennomm")
                                    }
                                }

                            }
                            recyclerAdapter.adapter = context?.let {
                                RecyclerAdapter(
                                    it,
                                    tittelListe,
                                    instruks1_Liste,
                                    instruks2_Liste,
                                    instruks3_Liste,
                                    bildeliste,
                                    type_liste
                                )
                            }
                            binding.swiperefresh.isRefreshing = false

                        } else {
                            Toast.makeText(context, "Fikk ikke resultater", Toast.LENGTH_LONG).show()
                            binding.swiperefresh.isRefreshing = false
                        }
                    }

                }
            }
            binding.swiperefresh.isRefreshing = false
        }


        if (arr != null) {
            spillArray = arr.toMutableList() as ArrayList<String>
        }
        else {
            println("Arr er null")
        }

        if (spillArray != null) {
            for (i in 0 until spillArray.size) {
                if(spillArray[i].contains("Dilemma",true))
                    leggTilSpill(spillArray[i] , "Velg mellom to goder eller onder", "Sosialt", "Gøy", R.mipmap.icon_dilemma, "Dilemma" )
                if(spillArray[i].contains("Quiz",true))
                    leggTilSpill(spillArray[i] , "Hvem sin lyrikk er det?", "Kunnskap", "Musikk", R.mipmap.icon_music, "Quiz" )
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
                if (sjekkBruker())
                    when (item.itemId) {
                        R.id.item_musikkquiz ->
                            view.findNavController()
                                .navigate(R.id.action_hovedMenyFragment_to_opprettMusikkQuizFragment)
                        R.id.item_dilemma ->
                            view.findNavController()
                                .navigate(R.id.action_hovedMenyFragment_to_opprettDilemmaFragment)
                    }
                else
                    Toast.makeText(context, "Du må ha en bruker for å opprette nye spill!", Toast.LENGTH_SHORT).show()
                true
            })

            popupMenu.show()

        }

        return binding.root
    }


    fun leggTilSpill(tittel: String, instruks1: String,instruks2: String,instruks3: String, bilde: Int, type : String ) {
        println("Legger til kort: $tittel")
        tittelListe.add(tittel)
        instruks1_Liste.add(instruks1)
        instruks2_Liste.add(instruks2)
        instruks3_Liste.add(instruks3)
        bildeliste.add(bilde)
        type_liste.add(type)
    }

    fun fjernKort() {
        println("Fjerner kort....")
        tittelListe.clear()
        instruks1_Liste.clear()
        instruks2_Liste.clear()
        instruks3_Liste.clear()
        bildeliste.clear()
        type_liste.clear()
    }

    fun fyllEksempelData() {
        leggTilSpill("Musikkquiz", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round, "Dilemma")
        leggTilSpill("Dilemma", "asdf", "asdf", "asdf", R.mipmap.ic_launcher_round, "Dilemma")
    }

    fun sjekkBruker() : Boolean {
        var user = app.currentUser();
        if (user != null) {
            if (user.profile.email != null) {
                return true
            }
        }
        return false
    }



}