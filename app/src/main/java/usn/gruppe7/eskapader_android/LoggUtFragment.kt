package usn.gruppe7.eskapader_android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


class LoggUtFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val i = Intent(context,LoginActivity::class.java)
        startActivity(i)
        Toast.makeText(context,"Du er nå logget ut",Toast.LENGTH_LONG).show()

        return inflater.inflate(R.layout.fragment_logg_ut, container, false)
    }


}