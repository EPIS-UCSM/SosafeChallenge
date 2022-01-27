package bimerso.sosafechallenge.ui.mapa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bimerso.sosafechallenge.ui.mapa.repository.MapaRepository

class MapaViewModelProviderFactory(
    val repository: MapaRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapaViewModel(repository) as T
    }
}
