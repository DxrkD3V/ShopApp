import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R
import com.example.tienda.model.CartItemDto
import com.example.tienda.recycler.AdapterCarrito
import com.example.tienda.viewmodel.CarritoViewModel

class Carrito : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterCarrito
    private val viewModel: CarritoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerCarrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterCarrito(
            dataSet = emptyList(),
            onSumarClick = { viewModel.sumarProducto(it) },
            onRestarClick = { cartItem, itemView ->
                if (cartItem.quantity > 1) {
                    viewModel.restarProducto(cartItem)
                } else {
                    mostrarDialogoEliminar(cartItem, itemView)
                }
            },
            onItemClick = { cartItem, itemView -> mostrarDialogoEliminar(cartItem, itemView) }
        )
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.carrito.collect { productos ->
                adapter.updateProductos(productos)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.mensaje.collect { mensaje ->
                mensaje?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.limpiarMensaje()
                }
            }
        }

        viewModel.cargarCarrito()
    }

    private fun mostrarDialogoEliminar(cartItem: CartItemDto, itemView: View) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar del carrito")
        builder.setMessage("¿Eliminar ${cartItem.productName} del carrito?")
        builder.setPositiveButton("Sí") { _, _ ->
            viewModel.eliminarProducto(cartItem)
            itemView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        }
        builder.setNegativeButton("No") { _, _ ->
            itemView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        }
        builder.show()
    }
}
