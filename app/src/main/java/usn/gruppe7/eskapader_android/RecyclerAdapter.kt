package usn.gruppe7.eskapader_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val context :Context, private val tittler: List<String>, private val instruks1: List<String>, private val instruks2: List<String>, private val instruks3: List<String>, private val bilde: List<Int>, private val type: MutableList<String>) :
RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.spillTittel)
        val itemInstruks1 : TextView = itemView.findViewById(R.id.instruks1)
        val itemInstruks2 : TextView = itemView.findViewById(R.id.instruks2)
        val itemInstruks3 : TextView = itemView.findViewById(R.id.instruks3)
        val bilde : ImageView = itemView.findViewById(R.id.spillKortBilde)
        val spillType = type

        init {


            itemView.setOnClickListener() {
                val kortTrykket : Int = adapterPosition
                if(this.spillType[kortTrykket]  == "Dilemma") {
                    val valgtSpill = this.itemTitle.text.toString()
                    val bundle = bundleOf("Spillnavn" to valgtSpill)
                    itemView.findNavController().navigate(R.id.action_hovedMenyFragment_to_dilemmaActivity, bundle)
                }
                if(this.spillType[kortTrykket] == "Quiz") {
                    val valgtSpill = this.itemTitle.text.toString()
                    val bundle = bundleOf("Spillnavn" to valgtSpill)
                    itemView.findNavController().navigate(R.id.action_hovedMenyFragment_to_musikkQuiz_Activity,bundle)
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