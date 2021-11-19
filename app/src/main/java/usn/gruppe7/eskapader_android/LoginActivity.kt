package usn.gruppe7.eskapader_android

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User

lateinit var app: App
class LoginActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var gjestLoginButton: Button
    private lateinit var createUserButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Realm.init(this)
        app = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .defaultSyncErrorHandler { session, error ->
                    Log.e(ContentValues.TAG, "Sync error: ${error.errorMessage}")
                }
                .build())

        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        Log.v(ContentValues.TAG, "Initialized the Realm App configuration for: ${app.configuration.appId}")

        val volley = APIConnector(this)


        username = findViewById(R.id.brukernavnInput)
        password = findViewById(R.id.passordInput)
        loginButton = findViewById(R.id.loggInnBtn)
        gjestLoginButton = findViewById(R.id.gjestLoggInnbt)
        createUserButton = findViewById(R.id.textView2)

        gjestLoginButton.setOnClickListener { view : View ->
            loggInnGjest(app)
        }

        createUserButton.setOnClickListener{view: View ->
            view?.findNavController()?.navigate(R.id.action_loggInnFragment_to_opprettBrukerFragment2)
        }

        loginButton.setOnClickListener { view: View ->
            Log.d("Debug", "Trykket på LOGIN")
            if (username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty())
                loggInn(app, username.text.toString(), password.text.toString(), view)
            else
                Log.v("X", "BRUKERNAVN ELLER PASSORD ER TOM!!!!")
        }
    }

    private fun loggInnGjest(app: App) {
        val credentials: Credentials = Credentials.anonymous()
        var user: User? = null
        app.loginAsync(credentials) {
            if (it.isSuccess) {
                user = app.currentUser()
                Log.d(ContentValues.TAG, "Successfully authenticated anonymously.")
                hentSpillOgGåVidere()

            } else {
                Log.e(ContentValues.TAG, it.error.toString())
            }
        }


    }

    private fun loggInn(app: App, username: String, password: String, view: View){
        val creds = Credentials.emailPassword(username, password)

        var user = app.currentUser()

        app.loginAsync(creds) {
            if (!it.isSuccess) {
                println("FEIL I INNLOGGING ")
            } else {
                println("KLARTE Å LOGGE INN")
                hentSpillOgGåVidere()
            }
        }
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e(ContentValues.TAG, errorMsg)
    }

    fun hentSpillOgGåVidere() {
        val volley =APIConnector(this)

        if (sjekkBruker()) {
            volley.hentAlleSpill { result ->
                println(result)
                println("Fikk result fra volley i logginn ")
                if (result != null) {
                    println("resultat er ikke null")
                    val sharedPreference = getSharedPreferences("SPILL_LISTE", Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putStringSet("Arr", result.toSet())
                    println("Legger med til hoved -> ${result.toSet()}")
                    editor.apply()
                }
                val intent = Intent(this, HovedMenyActivity::class.java)
                startActivity(intent)
            }
        }
        else {

        }

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