package bimerso.sosafechallenge.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bimerso.sosafechallenge.databinding.FragmentFavoritosBinding

class FragmentFavoritos : Fragment() {
    private lateinit var favoritoViewModel: FavoritosViewModel
    private lateinit var binding: FragmentFavoritosBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        favoritoViewModel = ViewModelProvider(this).get(FavoritosViewModel::class.java)
        binding= FragmentFavoritosBinding.inflate(inflater,container,false)
        return binding.root
    }
}