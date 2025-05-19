import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tienda.Navegacion
import com.example.tienda.databinding.ActivityMainBinding
import com.example.tienda.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.loginbtn.setOnClickListener {
            val email = binding.usuariologin.text.toString()
            val password = binding.passwordlogin.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login(email, password)
            }
        }

        loginViewModel.loginSuccess.observe(this) {
            if (it) {
                val intent = Intent(this, Navegacion::class.java)
                intent.putExtra("email", binding.usuariologin.text.toString())
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}
