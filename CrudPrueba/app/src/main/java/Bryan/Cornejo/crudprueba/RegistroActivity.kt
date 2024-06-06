package Bryan.Cornejo.crudprueba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        }
        btnRegresar.setOnClickListener {
            val Atras= Intent(this, MainActivity::class.java)
            startActivity(Atras)
        }

    }
}