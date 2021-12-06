package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
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
    private var valgtDilemma : Boolean = false
    private var valgtAlternativ: Int = 0
    private var enigMedFlertall: Int = 0

    @SuppressLint("SetTextI18n")
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
        val spillNavn = arguments?.getString("Spillnavn") as String

        binding.dillAlt1.text = dilemmaListe.get(currDilemma).alternativ[0]
        binding.dillAlt2.text = dilemmaListe.get(currDilemma).alternativ[1]

        val volley = APIConnector (requireContext())

        if (!valgtDilemma) {
            binding.dillAlt1.background = normalShape
            binding.dillAlt2.background = normalShape
        }

        //Du valgte alternativ 1
        binding.dillAlt1.setOnClickListener {
            valgtAlternativ = 0
            if (valgtAlternativ == 0 && valgtDilemma == false) {
                /*
                Siden API har en metode for å oppdatere globalt dilemma
                og vi lagde en metode i api for å oppdatere de nye datamodellene
                må vi skille mellom hvilken vi vil oppdatere. Derfor har vi med en sjekk for å vite hvilken
                metode i API som skal bli kalt.
                 */
                if (spillNavn == "Dilemma")
                    volley.oppdaterGlobalDilemma(currDilemma, valgtAlternativ, "testbruker")
                else
                    volley.oppdaterDilemma(spillNavn, currDilemma, valgtAlternativ)


                valgtDilemma = true
                binding.dillAlt1.background = korrektShape
                binding.dillAlt2.background = normalShape
                binding.btNesteDilemma.visibility = View.VISIBLE
            }

            else{}

            val stat1Total = dilemmaListe[currDilemma].statistikk[0].toDouble()
            val stat2Total = dilemmaListe[currDilemma].statistikk[1].toDouble()
            val totalStat =   stat1Total+stat2Total

            val statestikk1 = (stat1Total/totalStat)*100
            val statestikk2 = (stat2Total/totalStat)*100

            if(statestikk1 > statestikk2 && valgtAlternativ == 0)
                enigMedFlertall++

            binding.statestikk1.text = "Alternativ 1 er valgt ${Math.round(statestikk1)}% av tiden"
            binding.statestikk2.text = "mens Alternativ 2 er valgt ${Math.round(statestikk2)}%"

            }


        //Du valgte alternativ 2
        binding.dillAlt2.setOnClickListener{
            valgtAlternativ = 1
            if (valgtAlternativ == 1 && valgtDilemma == false) {

                if (spillNavn == "Dilemma")
                    volley.oppdaterGlobalDilemma(currDilemma, valgtAlternativ, "testbruker")
                else
                    volley.oppdaterDilemma(spillNavn, currDilemma, valgtAlternativ)

                valgtDilemma = true
                binding.dillAlt1.background = normalShape
                binding.dillAlt2.background = korrektShape
                binding.btNesteDilemma.visibility = View.VISIBLE
            }
            else{}

            val stat1Total = dilemmaListe[currDilemma].statistikk[0].toDouble()
            val stat2Total = dilemmaListe[currDilemma].statistikk[1].toDouble()
            val totalStat =   stat1Total+stat2Total.toDouble()

            val statestikk1 = (stat1Total/totalStat)*100
            val statestikk2 = (stat2Total/totalStat)*100

            if(statestikk2 > statestikk1 && valgtAlternativ == 1)
                enigMedFlertall++

            binding.statestikk1.text = "Alternativ 1 er valgt ${Math.round(statestikk1)}% av tiden"
            binding.statestikk2.text = "mens Alternativ 2 er valgt ${Math.round(statestikk2)}% "
        }


        //Neste dilemma btn
        binding.btNesteDilemma.setOnClickListener {
            currDilemma++
            if (currDilemma == dilemmaListe.size) {
                val stats = ((enigMedFlertall.toDouble() / dilemmaListe.size.toDouble())* 100)
                val dilemmaFerdigFragment = DilemmaFerdigFragment.newInstance(stats)
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.dilemma_Container, dilemmaFerdigFragment)
                fr?.commit()

            }

            else if (valgtDilemma == true) {
                    binding.btNesteDilemma.text = "Neste"
                    binding.dillAlt1.text = dilemmaListe[currDilemma].alternativ[0]
                    binding.dillAlt2.text = dilemmaListe[currDilemma].alternativ[1]
                    binding.dillAlt1.background = normalShape
                    binding.dillAlt2.background = normalShape
                    binding.btNesteDilemma.visibility = View.INVISIBLE
                    binding.statestikk1.text  = ""
                    binding.statestikk2.text  = ""
                    valgtDilemma = false

                }

            }


        // Returnerer binding for view
        return binding.root
    }

    companion object {
        fun newInstance(dilemmaListe : ArrayList<Dilemma>, spillNavn:String) =
            DilemmaFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("liste", dilemmaListe)
                    putString("Spillnavn", spillNavn)
                }
            }
    }



}

