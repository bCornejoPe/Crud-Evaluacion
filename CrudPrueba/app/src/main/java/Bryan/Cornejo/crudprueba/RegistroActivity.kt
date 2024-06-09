package Bryan.Cornejo.crudprueba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class RegistroActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoNuevo= findViewById<EditText>(R.id.txtUsuarioNuevo)
        val txtContrasenaNueva = findViewById<EditText>(R.id.txtContrasenaNueva)
        val btnRegresar = findViewById<ImageButton>(R.id.btnAtras)
        val btnRegistrar = findViewById<Button>(R.id.btnCrearUsuario)


        btnRegistrar.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO){
                val objConexion = ClaseConexion().cadenaConexion()

                val crearUsuario = objConexion?.prepareStatement("Insert into tbUsuarios(UUID_usuario, correoElectronico, clave) Values(?,?.?)")!!
                crearUsuario.setString(1,UUID.randomUUID().toString())
                crearUsuario.setString(2,txtCorreoNuevo.text.toString())
                crearUsuario.setString(3,txtContrasenaNueva.text.toString())
                crearUsuario.executeUpdate()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@RegistroActivity, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtCorreoNuevo.setText("")
                    txtContrasenaNueva.setText("")
                }
            }
        }
        btnRegresar.setOnClickListener {
            val Atras= Intent(this, MainActivity::class.java)
            startActivity(Atras)
        }

    }
}