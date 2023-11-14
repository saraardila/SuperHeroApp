package com.nawin.superheroapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.nawin.cursokotlin.superheroapp.SuperHeroAdapter
import com.nawin.superheroapp.databinding.ActivitySuperHeroListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroListActivity : AppCompatActivity(){


    //--> se llama igual que el activity pero al revés y hay que poner en el gradle module app el viewBinding.
    private lateinit var binding: ActivitySuperHeroListBinding //--> en vez de llamar a las vistas como siempre (FindByid) se utiliza el binding.
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuperHeroListBinding.inflate(layoutInflater) //--> se llama aqui al inflador.
        setContentView(binding.root)//--> se cambia el set por bingding.root


        retrofit = getRetrofit() //--> llamamos a la funcion donde hemos generado el retrofit
        initUI()
    }

    private fun initUI() {

        //--> y ahora se puede llamar a las pantallas y sus componentes directamente
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false //--> esto es un return false
        })

        //-->RecyclerView
        adapter = SuperHeroAdapter{superheroId -> navigateToDetail(superheroId)}
        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
    }

    private fun searchByName(query: String) {

        binding.progressbar.isVisible = true

        //Corrutina para que se realice en un hilo secundario. IO es para hilos secundarios cuando se hacen peticiones, o bbdd..etc
        CoroutineScope(Dispatchers.IO).launch {

            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperheroes(query)

            if(myResponse.isSuccessful) {

                Log.i("Sara", "funciona! :D")

                val response: SuperHeroDataResponse? = myResponse.body()

                if (response != null) {

                    Log.i("Sara", response.toString())
                    runOnUiThread{ //--> para que corra en el hilo principal

                        adapter.updateList(response.superheroes)
                        binding.progressbar.isVisible = false
                    }
                }
            }
            else{

                Log.i("Sara", "no funciona :(" )
            }
        }

    }


    private fun getRetrofit(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id:String){ //--> navegar a la segunda pantalla

        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(DetailSuperHeroActivity.EXTRA_ID, id) //-->> viene de la segunda panralla, del const val
        startActivity(intent)
    }
}