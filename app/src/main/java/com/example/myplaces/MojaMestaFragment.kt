package com.example.myplaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth


class MojaMestaFragment : Fragment() {
    private lateinit var MyPlaces:ArrayList<String>
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_moja_mesta,container,false)
        // Kreiranje niza s članovima "Tvrdjava" i "Bunker"
        val items = listOf("Tvrdjava", "Bunker")

        // Pronalazak ListView komponente iz XML-a
        val listView: ListView = view.findViewById(R.id.mestaView)

        // Kreiranje adaptera za ListView
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, items)

        // Postavljanje adaptera na ListView
        listView.adapter = adapter
        return view
    }



}