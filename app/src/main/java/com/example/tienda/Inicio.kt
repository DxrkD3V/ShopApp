package com.example.tienda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "url"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [web_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Inicio : Fragment() {
    // TODO: Rename and change types of parameters
    private var url: String? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM1)
            //           param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_inicio, container, false)
        var myWebView = myView.findViewById<WebView>(R.id.myWebView)
        myWebView.webViewClient = android.webkit.WebViewClient()
        myWebView.settings.javaScriptEnabled = true
        myWebView.loadUrl(url!!)
        return myView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment web_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(url: String) =
            Inicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, url)
                    //                   putString(ARG_PARAM2, param2)
                }
            }
    }
}