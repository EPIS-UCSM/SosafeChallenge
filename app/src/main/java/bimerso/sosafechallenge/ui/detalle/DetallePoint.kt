package bimerso.sosafechallenge.ui.detalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import bimerso.sosafechallenge.R
import bimerso.sosafechallenge.base.BaseActivity
import bimerso.sosafechallenge.base.Constants
import bimerso.sosafechallenge.databinding.ActivityDetallePointBinding
import bimerso.sosafechallenge.models.Lugares
import bimerso.sosafechallenge.ui.mapa.view.FragmentMapsDirections
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdate




class DetallePoint : BaseActivity() {
    private var googleMap: GoogleMap?=null
    private lateinit var binding:ActivityDetallePointBinding
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        binding.btnGuardar.setOnClickListener{
            Toast.makeText(applicationContext,"La funciondalidad de Room no pude completar",Toast.LENGTH_LONG).show()
        }
        binding.tvnombrePoint.text ="Nombre: " +Constants.entidad!!.name
        binding.tvdireccionPoint.text = "Dirección: "+Constants.entidad!!.vicinity
        binding.tvratingPoint.text = "Calificación: "+Constants.entidad!!.rating.toString()

        val marker = LatLng(Constants.entidad!!.geometry.location.lat, Constants.entidad!!.geometry.location.lng)
        googleMap!!.addMarker(MarkerOptions().position(marker).title(Constants.entidad!!.name))
        val zoom = CameraUpdateFactory.zoomTo(15f)
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.animateCamera(zoom)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePointBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetalle) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}