package bimerso.sosafechallenge.ui.mapa.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bimerso.sosafechallenge.Principal
import bimerso.sosafechallenge.R
import bimerso.sosafechallenge.base.BaseFragment
import bimerso.sosafechallenge.base.Constants
import bimerso.sosafechallenge.databinding.FragmentMapsBinding
import bimerso.sosafechallenge.models.Lugares
import bimerso.sosafechallenge.ui.mapa.repository.MapaRepository
import bimerso.sosafechallenge.ui.mapa.viewmodel.MapaViewModel
import bimerso.sosafechallenge.ui.mapa.viewmodel.MapaViewModelProviderFactory
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FragmentMaps : BaseFragment() {

    private var googleMap:GoogleMap?=null
    private var location:String=""
    private var keyword:String=""
    private lateinit var mapaViewModel: MapaViewModel
    private lateinit var binding: FragmentMapsBinding
    private var lista: ArrayList<Lugares>?=null
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        val marker = LatLng(Constants.latitud, Constants.longitud)
        googleMap!!.addMarker(MarkerOptions().position(marker).title("Mi posición"))
        val zoom = CameraUpdateFactory.zoomTo(11f)
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.animateCamera(zoom)
        binding.svBuscar.setOnClickListener{ binding.svBuscar.isIconified = false }
        binding.svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if(s.length > 1){
                    getPoints(s)
                }

                return false
            }
            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        binding.fabplaces.setOnClickListener{
            if(keyword == "" && location==""){
                showErrorMessage("Atención","Primero debe buscar algo")
            }
            else{
                val action = FragmentMapsDirections.actionNavMapaToFragmentListado(keyword,location)
                Navigation.findNavController(it).navigate(action)
            }

        }
    }
    private fun getPoints(keyword:String) {
        location ="${Constants.latitud},${Constants.longitud}"
        this.keyword = keyword
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
                        insertMarkers()
                    }
                }
                is MapaViewModel.ViewState.Failure->{
                    (parentActivity as Principal).hideProgressDialog()
                    showErrorMessage("Error",it.error)
                }
            }
        })
    }
    private fun insertMarkers() {
        googleMap!!.clear()
        for(entidad in lista!!){
            val marker = LatLng(entidad.geometry.location.lat, entidad.geometry.location.lng)
            googleMap!!.addMarker(MarkerOptions().position(marker).title(entidad.name))
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val repository = MapaRepository()
        val viewModelProviderFactory = MapaViewModelProviderFactory(repository)
        mapaViewModel = ViewModelProvider(this,viewModelProviderFactory).get(MapaViewModel::class.java)
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}