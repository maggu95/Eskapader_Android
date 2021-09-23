package usn.gruppe7.eskapader_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import usn.gruppe7.eskapader_android.databinding.FragmentOpprettBrukerBinding


class OpprettBrukerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentOpprettBrukerBinding>(inflater,R.layout.fragment_opprett_bruker,container,false)
        
        binding.bekreftBrukerbtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_opprettBrukerFragment_to_godkjentBruker)
        }
        return binding.root
    }


}