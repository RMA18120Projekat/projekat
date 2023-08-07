package com.example.myplaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var textIme:TextView
    lateinit var database:DatabaseReference
    val sharedViewModel:Korisnicko by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home,container,false)
        textIme=view.findViewById(R.id.textViewKorisnikHome)
        textIme.text=sharedViewModel.ime.toString()
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController())||super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu,menu)
    }


}