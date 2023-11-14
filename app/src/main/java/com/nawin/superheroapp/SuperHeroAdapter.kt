package com.nawin.cursokotlin.superheroapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nawin.superheroapp.R
import com.nawin.superheroapp.SuperheroItemResponse

class SuperHeroAdapter(var superheroList: List<SuperheroItemResponse> = emptyList(),
                       private val onItemSelected:(String) -> Unit ) //--> funcion lambda
                       : RecyclerView.Adapter<SuperHeroViewHolder>() {

    fun updateList(superheroList: List<SuperheroItemResponse>){

        this.superheroList = superheroList

        notifyDataSetChanged() //--> para que refesque a ver si los datos han cambiado
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return  SuperHeroViewHolder(layoutInflater.inflate(R.layout.item_superhero, parent, false))

    }

    override fun onBindViewHolder(viewholder: SuperHeroViewHolder, position: Int) {

            val item = superheroList[position]
            viewholder.bind(superheroList[position], onItemSelected)
    }

    override fun getItemCount() = superheroList.size

}