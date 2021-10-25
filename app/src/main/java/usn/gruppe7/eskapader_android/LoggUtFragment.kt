package usn.gruppe7.eskapader_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.User


class LoggUtFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var user = app.currentUser();
        if (user != null) {
            loggUtGjest(app, user)
        }
        else
            Log.v("X","INGEN BRUKER ATM!");

        // Inflate the layout for this fragment
        val i = Intent(context,LoginActivity::class.java)
        startActivity(i)
        Toast.makeText(context,"Du er nå logget ut",Toast.LENGTH_LONG).show()

        return inflater.inflate(R.layout.fragment_logg_ut, container, false)
    }

    private fun loggUtGjest(app: App, user: User) {
        val credentials: Credentials = Credentials.anonymous()
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                val user = it.get()
                user.removeAsync { result: App.Result<User?> ->
                    if (result.isSuccess) {
                        Log.v(
                            "EXAMPLE",
                            "Successfully removed user from device."
                        )
                    } else {
                        Log.e("EXAMPLE", "Failed to remove user from device.")
                    }
                }
            } else {
                Log.e("EXAMPLE", "Failed to log in: ${it.error.errorMessage}")
            }
        }
    }
}