package bimerso.sosafechallenge.ui.mapa.repository


import bimerso.sosafechallenge.network.RetrofitInstance

class MapaRepository (){
    suspend fun getPoints(location:String,radius:String,keyword:String) = RetrofitInstance.api.searchPoints(location,radius,keyword)

}