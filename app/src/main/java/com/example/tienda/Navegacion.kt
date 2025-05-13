package com.example.tienda

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.tienda.databinding.ActivityMainBinding
import com.example.tienda.databinding.ActivityNavegacionBinding
import com.example.tienda.utils.TokenManager
import com.google.android.material.navigation.NavigationView

class Navegacion : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityNavegacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNavegacionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding){
            setSupportActionBar(myToolbar)
            val toggle = ActionBarDrawerToggle(this@Navegacion, main, myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
            toggle.drawerArrowDrawable.color = ContextCompat.getColor(this@Navegacion, android.R.color.white)
            main.addDrawerListener(toggle)
            toggle.syncState()
            
            val emailRecepcion = intent.getStringExtra("email")
            val usernametext = emailRecepcion?.substringBefore("@") ?: "Usuario"
            username.text = usernametext
            
            val fragmentManagerInicio: FragmentManager = supportFragmentManager
            val fragmentTransactionInicio: FragmentTransaction = fragmentManagerInicio.beginTransaction()
            val fragmentInicio: Inicio = Inicio.newInstance("http://10.0.2.2:8000/")
            fragmentTransactionInicio.add(R.id.myLinearL, fragmentInicio).commit()

            myNavegationView.setNavigationItemSelectedListener { menuItem ->
                if (menuItem.itemId == R.id.id_productos) {
                    val productosFragment = Productos()

                    val myFragmentManager: FragmentManager = supportFragmentManager
                    val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()

                    myFragmentTransaction.replace(R.id.myLinearL, productosFragment)
                        .addToBackStack(null)
                        .commit()
                }else if(menuItem.itemId == R.id.id_inicio){
                    val myFragmentManager: FragmentManager = supportFragmentManager
                    val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()
                    val myFragment: Inicio = Inicio.newInstance("http://10.0.2.2:8000/")
                    myFragmentTransaction.replace(R.id.myLinearL, myFragment).commit()
                }else if (menuItem.itemId == R.id.id_carrito) {
                    val myFragmentManager: FragmentManager = supportFragmentManager
                    val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()
                    val myFragment = Carrito()
                    myFragmentTransaction.replace(R.id.myLinearL, myFragment).commit()
                }

                binding.main.closeDrawer(GravityCompat.START)

                true
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}