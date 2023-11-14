package com.nawin.cursokotlin.superheroapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nawin.superheroapp.SuperheroItemResponse
import com.nawin.superheroapp.databinding.ItemSuperheroBinding

import com.squareup.picasso.Picasso

class SuperHeroViewHolder(view:View): RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected:(String) -> Unit){ //--> le pasamos la funcion lambda

        binding.tvSuperHeroName.text = superheroItemResponse.superheroName
        binding.ivSuperHero

        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivSuperHero)

        //--> Para recuperar el id de superheroe cuando de click
        binding.root.setOnClickListener{onItemSelected(superheroItemResponse.superheroId)} //--> con root le pasamos toda la lista
    }
}