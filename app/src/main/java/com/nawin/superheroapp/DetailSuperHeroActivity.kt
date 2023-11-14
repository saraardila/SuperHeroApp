package com.nawin.superheroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.nawin.superheroapp.databinding.ActivityDetailSuperHeroBinding

import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.math.roundToInt

//cambios ok.
class DetailSuperHeroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailSuperHeroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperheroInformation(id)
    }

    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail =
                getRetrofit().create(ApiService::class.java).getSuperHeroDetail(id)

            if(superheroDetail.body() != null){
                runOnUiThread { createUI(superheroDetail.body()!!) }
            }
        }
    }

    private fun createUI(superhero: SuperHeroDetailResponse) {
        Picasso.get().load(superhero.image.url).into(binding.ivSuperHero)
        binding.tvSuperHeroName.text = superhero.name
        prepareStats(superhero.powerstats)
        binding.tvSuperHeroRealName.text = superhero.biography.fullName
        binding.tvPublisher.text = superhero.biography.publisher
        binding.tvBirth.text = superhero.biography.birth
        binding.tvAppearance.text = superhero.biography.appearance
        binding.tvOcupation.text = superhero.work.occupation
        binding.tvBase.text = superhero.work.base
    }

    private fun prepareStats(powerStats: PowerStatsResponse) {

        updateHeight(binding.viewCombat, powerStats.combat) //para que vayan cambiando
        updateHeight(binding.viewDurability, powerStats.durability)
        updateHeight(binding.viewSpeed, powerStats.speed)
        updateHeight(binding.viewPower, powerStats.power)
        updateHeight(binding.viewIntelligence, powerStats.intelligence)
        updateHeight(binding.viewStrength, powerStats.strength)

    }

    private fun updateHeight(view: View, stat:String){ //--> metodo para ir cambiando los componentes

        val params = view.layoutParams

        params.height = pxToDp(stat.toFloat()) //recibo la vista que llega por parametro

       view.layoutParams = params //--> a la vista que me llega le paso los params

    }

    private fun pxToDp(px:Float):Int{ //--> convertir los px en un dp validos.
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
