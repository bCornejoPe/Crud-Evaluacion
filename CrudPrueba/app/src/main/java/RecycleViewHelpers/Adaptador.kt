package RecycleViewHelpers

import Bryan.Cornejo.crudprueba.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData.Item
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.Ticket

class Adaptador(var Datos: List<Ticket>): RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista: List<Ticket>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun ActualizarListaDespuesDeActualizarDatos(uuid: String, nuevoNombre: String) {
        val index = Datos.indexOfFirst { it.uuid == uuid }
        Datos[index].Titulo = nuevoNombre
        notifyItemChanged(index)
    }

    fun eliminarRegistro(Titulo: String, posicion: Int) {


        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()
            val deleteTitulo = objConexion?.prepareStatement("Delete Ticket where Titulo = ?")!!
            deleteTitulo.setString(1, Titulo)
            deleteTitulo.executeUpdate()


            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }
    fun actualizarticket(Titulo: String, uuid: String){
        //1-Creo un acorrutina
        GlobalScope.launch(Dispatchers.IO){

            val objConexion = ClaseConexion().cadenaConexion()

            val updateticket= objConexion?.prepareStatement("update ticket set Titulo = ? where uuid= ?")!!
            updateticket.setString(1,Titulo)
            updateticket.setString(2,uuid)
            updateticket.executeUpdate()


            val commit = objConexion?.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarListaDespuesDeActualizarDatos(uuid, Titulo)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_crads, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val Titulos = Datos[position]
        holder.txtNombreCard.text = Titulos.Titulo



        val item = Datos[position]
        holder.imgBorrar.setOnClickListener {


            val context = holder.itemView.context


            val builder = AlertDialog.Builder(context)

            //A mi alerta le pongo un titulo
            builder.setTitle("¿Estas seguro?")

            //Ponerle un mensaje
            builder.setMessage("¿Desea eliminar el registro?")

            //Paso final, agregamos los botones
            builder.setPositiveButton("SI") { dialog, wich ->
                eliminarRegistro(item.Titulo, position)
            }

            builder.setNegativeButton("No"){dialog, wich ->
            }
            //Creamos la alerta
            val alertDialog = builder.create()
            //Mostramos la alerta
            alertDialog.show()
        }

        holder.imgEditar.setOnClickListener {
            val context= holder.itemView.context

            //Creo la alerta
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar Nombre")

            //Agregamos un cuadro de texto
            //pueda escribir el nuevo nombre
            val cuadritoNuevoNombre= EditText(context)
            cuadritoNuevoNombre.setHint(item.Titulo)
            builder.setView(cuadritoNuevoNombre)

            builder.setPositiveButton("Actualizar"){ dialog, wich->
                actualizarticket(cuadritoNuevoNombre.text.toString(), item.uuid)
            }

            builder.setNegativeButton("Cancelar"){dialog, wich ->
                dialog.dismiss()
            }

            val dialog= builder.create()
            dialog.show()
        }
        //Darle click a la cart
        holder.itemView.setOnClickListener {  }

    }


    }

