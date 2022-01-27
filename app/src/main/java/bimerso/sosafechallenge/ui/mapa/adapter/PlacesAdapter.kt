package bimerso.sosafechallenge.ui.mapa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bimerso.sosafechallenge.R
import bimerso.sosafechallenge.models.Lugares
class PlacesAdapter(val context: Context, var list: ArrayList<Lugares>,
                    val listener:(entidad:Lugares)->Unit): RecyclerView.Adapter<PlacesViewHolder>() {

    var listSearch = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_places, parent, false)
        return PlacesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.tvNombre?.text = list[position].name
        holder.tvDireccion?.text = list[position].vicinity
        holder.container_places?.setOnClickListener{
            listener(list[position])
        }
    }
}