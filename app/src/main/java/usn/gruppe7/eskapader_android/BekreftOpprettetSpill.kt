package usn.gruppe7.eskapader_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import usn.gruppe7.eskapader_android.databinding.FragmentBekreftOpprettetSpillBinding


class BekreftOpprettetSpill : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentBekreftOpprettetSpillBinding>(inflater, R.layout.fragment_bekreft_opprettet_spill,container,false)
        val spillNavn =  arguments?.getString("Spillnavn") as String
        binding.spillBeskrivelseTxt1.text = "Spill som ble opprettet:"
        binding.spillBeskrivelseTxt2.text = "$spillNavn"

        binding.tilbakeTIilStartBtn.setOnClickListener {
            val intent = Intent(context, HovedMenyActivity::class.java)
            startActivity(intent)
        }
        

        return binding.root
    }

    companion object {

        fun newInstance(Spillnavn : String) =
            BekreftOpprettetSpill().apply {
                arguments = Bundle().apply {

                }
            }
    }
}