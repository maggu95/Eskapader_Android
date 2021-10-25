package usn.gruppe7.eskapader_android

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User

lateinit var app: App

class RealmInit: Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the Realm SDK
        Realm.init(this)

        /*
        val appID =  "authenticationdemo-wgbmm" // replace this with your App ID
        app = App(
            AppConfiguration.Builder(appID)
                .build()
        )
        */


        app = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .defaultSyncErrorHandler { session, error ->
                    Log.e(TAG, "Sync error: ${error.errorMessage}")
                }
                .build())

        // Enable more logging in debug mode
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        Log.v(TAG, "Initialized the Realm App configuration for: ${app.configuration.appId}")


    }
}