package bimerso.sosafechallenge.ui.mapa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import bimerso.sosafechallenge.models.PointsResponse
import bimerso.sosafechallenge.ui.mapa.repository.MapaRepository
import kotlinx.coroutines.Dispatchers

class MapaViewModel(private val repository: MapaRepository) : ViewModel() {
    sealed class ViewState{
        object Loading: ViewState()
        data class Failure(val error:String) : ViewState()
        data class GetPoints(val entidad:PointsResponse) : ViewState()
    }

    fun getPoints(location:String,keyword:String) = liveData(Dispatchers.IO){
        emit(ViewState.Loading)
        try {
            val response = repository.getPoints(location,"10",keyword)
            if (response.isSuccessful) {
                response.body()?.let { resultResponse->
                    if(resultResponse.status == "REQUEST_DENIED")
                        emit(ViewState.Failure("Ocurrió un error intente mas tarde"))
                    else
                        emit(ViewState.GetPoints(resultResponse))
                }
            }
            else
                emit(ViewState.Failure(response.errorBody().toString()))
        }
        catch (e:Exception){
            emit(ViewState.Failure(e.toString()))
        }
    }
}