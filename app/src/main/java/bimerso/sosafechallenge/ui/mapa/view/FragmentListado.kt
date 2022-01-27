package bimerso.sosafechallenge.ui.mapa.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bimerso.sosafechallenge.Principal

import bimerso.sosafechallenge.base.BaseFragment
import bimerso.sosafechallenge.base.Constants
import bimerso.sosafechallenge.databinding.FragmentListadoBinding
import bimerso.sosafechallenge.models.Lugares
import bimerso.sosafechallenge.ui.detalle.DetallePoint
import bimerso.sosafechallenge.ui.mapa.adapter.PlacesAdapter
import bimerso.sosafechallenge.ui.mapa.repository.MapaRepository
import bimerso.sosafechallenge.ui.mapa.viewmodel.MapaViewModel
import bimerso.sosafechallenge.ui.mapa.viewmodel.MapaViewModelProviderFactory

class FragmentListado : BaseFragment() {
    var adapterPlaces: PlacesAdapter? = null
    private var location:String=""
    private var keyword:String=""
    private lateinit var mapaViewModel: MapaViewModel
    private lateinit var binding: FragmentListadoBinding
    private var lista: ArrayList<Lugares>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val repository = MapaRepository()
        val viewModelProviderFactory = MapaViewModelProviderFactory(repository)
        mapaViewModel = ViewModelProvider(this,viewModelProviderFactory).get(MapaViewModel::class.java)
        binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        location = FragmentListadoArgs.fromBundle(requireArguments()).location
        keyword = FragmentListadoArgs.fromBundle(requireArguments()).keyword
        getPoints(keyword)
    }
    private fun getPoints(keyword:String) {
        mapaViewModel.getPoints(location,keyword).observe(viewLifecycleOwner,{
            when (it){
                is MapaViewModel.ViewState.Loading->{
                    (parentActivity as Principal).showProgressDialog()
                }
                is MapaViewModel.ViewState.GetPoints->{
                    (parentActivity as Principal).hideProgressDialog()
                    if(it.entidad.status == "ZERO_RESULTS")
                        showErrorMessage("Mensaje","No se encontraron resultados")
                    else{
                        lista = it.entidad.results as ArrayList<Lugares>
                        setAdapterPlaces()
                    }
                }
                is MapaViewModel.ViewState.Failure->{
                    (parentActivity as Principal).hideProgressDialog()
                    showErrorMessage("Error",it.error)
                }
            }
        })
    }

    private fun setAdapterPlaces() {
        adapterPlaces = PlacesAdapter(requireContext(),lista!!){
            Constants.entidad = it
            val intent = Intent(parentActivity, DetallePoint::class.java)
            startActivity(intent)
        }
        binding.rvPlaces.adapter = adapterPlaces
    }
}