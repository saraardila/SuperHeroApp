
package com.nawin.superheroapp

import com.google.gson.annotations.SerializedName


//Respuesta

// el valor response, se llama asi por que el json de la api se llama asi ese valor, tiene que hacer match siempre.
//Con serializedName podemos cambiarle el nombre

//-->Este seria el inicio del JSON, la raiz
data class SuperHeroDataResponse(@SerializedName("response") val response: String,
                                 @SerializedName("results") val superheroes: List <SuperheroItemResponse>
                                 )

// y esta ya seria una rama o un objeto dentro de 'results', para poder acceder a el:

data class SuperheroItemResponse(@SerializedName("id") val superheroId: String,
                                 @SerializedName("name") val superheroName: String,
                                 @SerializedName("image") val superheroImage: SuperheroImageResponse
                                 )

data class SuperheroImageResponse(@SerializedName("url") val url: String

)