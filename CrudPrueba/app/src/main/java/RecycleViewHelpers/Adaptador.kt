package RecycleViewHelpers

import Bryan.Cornejo.crudprueba.R
import android.content.ClipData.Item
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import modelo.Ticket

class Adaptador(var Datos: List<Ticket>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_crads,parent,false)
        return ViewHolder(vista)
        }

    override fun getItemCount()= Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = Datos[position]
        holder.txtNombreCard.text= item.Titulo
    }

}