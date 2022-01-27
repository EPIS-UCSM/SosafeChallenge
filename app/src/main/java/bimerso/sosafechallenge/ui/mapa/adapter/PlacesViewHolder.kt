package bimerso.sosafechallenge.ui.mapa.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import bimerso.sosafechallenge.R

class PlacesViewHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
    var tvNombre = itemView?.findViewById<TextView>(R.id.tvNombre)
    var tvDireccion = itemView?.findViewById<TextView>(R.id.tvDireccion)
    var container_places = itemView?.findViewById<LinearLayout>(R.id.container_places)
}
