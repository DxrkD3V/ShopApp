import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R
import com.example.tienda.recycler.AdapterProducto
import com.example.tienda.viewmodel.ProductosViewModel

class Productos : Fragment() {
    private lateinit var viewModel: ProductosViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterProducto
    private lateinit var spinnerCategorias: Spinner
    private lateinit var textPageNumber: TextView
    private lateinit var buttonPrev: ImageButton
    private lateinit var buttonNext: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductosViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerProductos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterProducto(emptyList())
        recyclerView.adapter = adapter

        spinnerCategorias = view.findViewById(R.id.spinnerCategorias)
        textPageNumber = view.findViewById(R.id.textPageNumber)
        buttonPrev = view.findViewById(R.id.buttonPrev)
        buttonNext = view.findViewById(R.id.buttonNext)

        buttonPrev.setOnClickListener {
            viewModel.paginaAnterior()
        }

        buttonNext.setOnClickListener {
            viewModel.paginaSiguiente()
        }

        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val nombres = categorias.map { it.name }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategorias.adapter = spinnerAdapter
        }

        viewModel.productos.observe(viewLifecycleOwner) {
            adapter.updateProductos(it)
        }

        viewModel.paginaActual.observe(viewLifecycleOwner) { pagina ->
            textPageNumber.text = (pagina + 1).toString()
        }

        viewModel.totalPaginas.observe(viewLifecycleOwner) { totalPaginas ->
            val pagina = viewModel.paginaActual.value ?: 0
            buttonNext.isEnabled = pagina < totalPaginas - 1
            buttonPrev.isEnabled = pagina > 0
        }

        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.seleccionarCategoria(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        viewModel.cargarCategorias()
        viewModel.cargarProductos()
    }
}
