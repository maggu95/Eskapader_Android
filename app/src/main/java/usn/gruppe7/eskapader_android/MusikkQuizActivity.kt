package usn.gruppe7.eskapader_android

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.musikkquiz_spill)

        // Henter border fra my_border.xml
        val shape: Drawable? = getDrawable(R.drawable.rounded_borders_musikkquiz)

        val sangtekst = findViewById<TextView>(R.id.sangtekst)

        val txt1 = findViewById<TextView>(R.id.musAlt1)
        txt1.setOnClickListener() {
            txt1.background = shape
        }

        val txt2 = findViewById<TextView>(R.id.musAlt2)
        txt2.setOnClickListener() {
            txt2.background = shape
        }

        val txt3 = findViewById<TextView>(R.id.musAlt3)
        txt3.setOnClickListener() {
            txt3.background = shape
        }

        val txt4 = findViewById<TextView>(R.id.musAlt4)
        txt4.setOnClickListener() {
            txt4.background = shape
        }


        val btBekreft = findViewById<Button>(R.id.btBekreft)
        btBekreft.setOnClickListener() {
            sangtekst.setText(R.string.sangtekst2)
            txt1.setText(R.string.musAltTekst2_1)
            txt2.setText(R.string.musAltTekst2_2)
            txt3.setText(R.string.musAltTekst2_3)
            txt4.setText(R.string.musAltTekst2_4)
        }






    }
}