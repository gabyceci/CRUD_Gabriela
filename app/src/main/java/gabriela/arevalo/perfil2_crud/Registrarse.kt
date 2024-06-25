package gabriela.arevalo.perfil2_crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import java.util.UUID

class Registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val txtIniciaSesion = findViewById<TextView>(R.id.txtIniciaSesion)

        fun hashSHA256(contraseniaEscrita: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraseniaEscrita.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        btnRegistrarse.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                //Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                val contraseniaEncriptada = hashSHA256(txtContrasena.text.toString())

                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO Usuarios(UUID_usuario, Correo, Contraseña) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreo.text.toString())
                crearUsuario.setString(3, contraseniaEncriptada)
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {

                    Toast.makeText(this@Registrarse, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtCorreo.setText("")
                    txtContrasena.setText("")
                }
            }
        }
        txtIniciaSesion.setOnClickListener {
            val pantallaIniciarSesion = Intent(this, IniciarSesion::class.java)
            startActivity(pantallaIniciarSesion)
        }
    }
}