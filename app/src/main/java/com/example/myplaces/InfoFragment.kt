package com.example.myplaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

class InfoFragment : Fragment() {
    lateinit var ime:TextView
    lateinit var slika:ImageView
    lateinit var prezime:TextView
    lateinit var brojTelefona:TextView
    lateinit var email:TextView
    lateinit var bodovi:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_info,container,false)
        setHasOptionsMenu(true)

        return view
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        findNavController().navigate(R.id.action_infoFragment_to_editFragment)
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController())||super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.edit,menu)
    }


}