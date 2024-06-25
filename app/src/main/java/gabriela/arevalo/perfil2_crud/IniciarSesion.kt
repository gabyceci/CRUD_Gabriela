package gabriela.arevalo.perfil2_crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import java.security.MessageDigest

class IniciarSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoI = findViewById<EditText>(R.id.txtCorreoI)
        val txtContrasenaI = findViewById<EditText>(R.id.txtContrasenaI)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val txtRegistrate = findViewById<TextView>(R.id.txtRegistrate)

        fun hashSHA256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        btnIngresar.setOnClickListener {
            val pantallaPrincipal = Intent(this, PaginaInicio::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val contrasenaEncriptada = hashSHA256(txtContrasenaI.text.toString())



                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM Usuarios WHERE Correo = ? AND Contraseña = ?")!!
                comprobarUsuario.setString(1, txtCorreoI.text.toString())
                comprobarUsuario.setString(2, contrasenaEncriptada)
                val resultado = comprobarUsuario.executeQuery()
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@IniciarSesion, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        println("contraseña $contrasenaEncriptada")
                    }

                }
            }
        }


        txtRegistrate.setOnClickListener {
            val pantallaRegistrarse = Intent(this, Registrarse::class.java)
            startActivity(pantallaRegistrarse)
        }

    }
}