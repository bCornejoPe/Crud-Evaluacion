package Bryan.Cornejo.crudprueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
         val btnIniciar= findViewById<Button>(R.id.btnIniciar)
        val btnUsuario = findViewById<Button>(R.id.btnUsario)

        //boton pasar pantalla de bienvenida
           btnIniciar.setOnClickListener {
               val PantallaInicio = Intent(this, BienvenidaActivity::class.java)
               GlobalScope.launch (Dispatchers.IO){
                   val objConexion =   ClaseConexion().cadenaConexion()

                   val ComprobarUsuario = objConexion?.prepareStatement("Select * from tbUsuarios where correoElectronico =? and clave=?")!!
                   ComprobarUsuario.setString(1,txtCorreo.text.toString())
                   ComprobarUsuario.setString(2,txtContrasena.text.toString())

                   val resultado= ComprobarUsuario.executeQuery()

                   if(resultado.next()){
                       startActivity(PantallaInicio)
                   }else{
                       println("Usuario no encontrado, verifique credenciales")
                   }
               }
           }
           btnUsuario.setOnClickListener {
               val PantallaUsuario= Intent(this,RegistroActivity:: class.java )
               startActivity(PantallaUsuario)
           }

    }
}