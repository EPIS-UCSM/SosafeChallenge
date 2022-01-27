package bimerso.sosafechallenge

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import bimerso.sosafechallenge.base.BaseActivity
import bimerso.sosafechallenge.base.Constants
import bimerso.sosafechallenge.databinding.ActivityPrincipalBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Principal : BaseActivity(),GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionRequestAccessFineLocation = 1
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGoogleApiClient()
        requestLocationPermission()
    }
    private fun goToLocation(){
        showProgressDialog()
        mGoogleApiClient.connect()
    }
    override fun onConnectionSuspended(p0: Int) {}

    override fun onConnectionFailed(p0: ConnectionResult) {}
    private fun requestLocationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (quickValidatePermission(Manifest.permission.ACCESS_FINE_LOCATION) && quickValidatePermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                goToLocation()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), permissionRequestAccessFineLocation)
            }
        } else { goToLocation() }
    }
    private fun quickValidatePermission(MANIFEST_PERMISSION : String) : Boolean{
        return ContextCompat.checkSelfPermission(this, MANIFEST_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            goToLocation()
        }
    }

    private fun initGoogleApiClient(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {
        mFusedLocationClient.lastLocation.addOnSuccessListener {
            if(it != null){
                hideProgressDialog()
                Constants.latitud=it.latitude
                Constants.longitud=it.longitude
                binding = ActivityPrincipalBinding.inflate(layoutInflater)
                setContentView(binding.root)
                setSupportActionBar(binding.appBarPrincipal.toolbar)
                val drawerLayout: DrawerLayout = binding.drawerLayout

                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.nav_mapa, R.id.nav_favoritos
                    ), drawerLayout
                )
                val navView: NavigationView = binding.navView
                val navController = findNavController(R.id.nav_host_fragment_content_principal)
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            }
        }
    }
}