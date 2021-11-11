package usn.gruppe7.eskapader_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val tittler: List<String>, private val instruks1 : List<String> ,private val instruks2: List<String> ,private val instruks3 : List<String>, private val bilde : List<Int> ) :
RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.spillTittel)
        val itemInstruks1 : TextView = itemView.findViewById(R.id.instruks1)
        val itemInstruks2 : TextView = itemView.findViewById(R.id.instruks2)
        val itemInstruks3 : TextView = itemView.findViewById(R.id.instruks3)
        val bilde : ImageView = itemView.findViewById(R.id.spillKortBilde)

        init {
            itemView.setOnClickListener() {
                val kortTrykket : Int = adapterPosition
                when (kortTrykket) {
                    0 -> itemView.findNavController().navigate(R.id.action_hovedMenyFragment_to_musikkQuiz_Activity)
                    1 -> itemView.findNavController().navigate(R.id.action_hovedMenyFragment_to_dilemmaActivity)
                }
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.spill_kort,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = tittler[position]
        holder.itemInstruks1.text = instruks1[position]
        holder.itemInstruks2.text = instruks2[position]
        holder.itemInstruks3.text = instruks3[position]
        holder.bilde.setImageResource(bilde[position])

    }

    override fun getItemCount(): Int {
        return tittler.size
    }


}