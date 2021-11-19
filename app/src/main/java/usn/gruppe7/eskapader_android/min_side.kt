package usn.gruppe7.eskapader_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.R
import android.widget.AdapterView

import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentMinSideBinding
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettDilemmaBinding
import java.text.FieldPosition


class min_aide : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : FragmentMinSideBinding
    var spillListe: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentMinSideBinding>(inflater,
            usn.gruppe7.eskapader_android.R.layout.fragment_min_side,container,false)

        val volley = context?.let {APIConnector(it)}

        val user = app.currentUser()
        val author = user?.profile?.email.toString()

        var spinner = binding.spillSpinner


        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, spillListe) }

        if (volley != null) {
            volley.hentMineSpill(author){ result ->
                if (result != null) {
                    spillListe = result
                    print(spillListe)
                    adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, spillListe) }
                    spinner.adapter = adapter
                }

            }
        }

        spinner.onItemSelectedListener = this


        var spillType :String = ""



        /*
        binding.btSlettSpill.setOnClickListener {
            val spillNavn = binding.txtInfoSpill.text.toString()
            if (binding.txtInfoSpill.text.toString().contains("Dilemma")) {
                spillType = "Dilemma"
            }
            else
                spillType = "Quiz"
            println("Spilltype: " + spillType)
            println("Spillnavn:" + spillNavn)
            println("Author: " + author)
            volley?.slettSpill(spillNavn, author, spillType) {
                result ->
                    if (result == true)
                        Toast.makeText(context, "Slettet spill!" , Toast.LENGTH_LONG).show()
            }
        }

         */
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
        binding?.txtInfoSpill?.setText(spillListe.get(position))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}