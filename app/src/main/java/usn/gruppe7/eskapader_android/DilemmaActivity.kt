package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil

class DilemmaActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dilemma_layout)

        val shape: Drawable? = getDrawable(R.drawable.rounded_borders_musikkquiz)
        val dilemma1 = findViewById<TextView>(R.id.dill_alt_1)
        val dilemma2 = findViewById<TextView>(R.id.dill_alt_2)
        val btNesteDilemma = findViewById<Button>(R.id.btNesteDilemma)

        dilemma1.setOnClickListener() {
            btNesteDilemma.visibility = View.VISIBLE
            dilemma1.background = shape
        }

        dilemma2.setOnClickListener() {
            dilemma2.background = shape
            btNesteDilemma.visibility = View.VISIBLE

        }

        btNesteDilemma.setOnClickListener() {
            dilemma1.setText(R.string.dilemmatekst2_1)
            dilemma2.setText(R.string.dilemmatekst2_2)
        }

    }
}