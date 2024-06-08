package Bryan.Cornejo.crudprueba

import RecycleViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.Ticket
import java.util.UUID

class Tickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreoAutor)
        val txtFecha = findViewById<EditText>(R.id.txtCreacion)
        val txtEstado = findViewById<EditText>(R.id.txtEstado)
        val txtFinal = findViewById<EditText>(R.id.txtFechaFinal)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarTicket)

        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager = LinearLayoutManager(this)

        fun obtenerTickets(): List<Ticket>{
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet= statement?.executeQuery("SELECT * FROM Ticket")!!

            val ListaTickets= mutableListOf<Ticket>()

            while (resultSet.next()) {
                val uuid = resultSet.getString("uuid")
                val Autor = resultSet.getString("Autor")
                val Titulo = resultSet.getString("Titulo")
                val Descripcion = resultSet.getString("Descripcion")
                val CorreoAutor = resultSet.getString("CorreoAutor")
                val FechaCreacion = resultSet.getString("FechaCreacion")
                val Estado = resultSet.getString("Estado")
                val FechaFinalizacion = resultSet.getString("FechaFinalizacion")

                val valoresjuntos = Ticket(
                    uuid,
                    Autor,
                    Titulo,
                    Descripcion,
                    CorreoAutor,
                    FechaCreacion,
                    Estado,
                    FechaFinalizacion
                )

                ListaTickets.add(valoresjuntos)
            }
            return ListaTickets
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticketsDB= obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter= Adaptador(ticketsDB)
                rcvTickets.adapter= adapter
            }
        }


        //Programar btnAgregar
        btnAgregar.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = ClaseConexion().cadenaConexion()

                val AgregadoTicket = objConexion?.prepareStatement("Insert into ticket (uuid, Autor, Titulo, Descripcion, CorreoAutor, FechaCreacion, Estado, FecjaFinalizacion) values(?,?,?,?,?,?,?,?)")!!

                AgregadoTicket.setString(1,UUID.randomUUID().toString())
                AgregadoTicket.setString(2, txtAutor.text.toString())
                AgregadoTicket.setString(3, txtTitulo.text.toString())
                AgregadoTicket.setString(4, txtDescripcion.text.toString())
                AgregadoTicket.setString(5, txtCorreo.text.toString())
                AgregadoTicket.setString(6,txtFecha.text.toString())
                AgregadoTicket.setString(7, txtEstado.text.toString())
                AgregadoTicket.setString(8, txtFinal.text.toString())
                AgregadoTicket.executeUpdate()
            }

        }

    }
}