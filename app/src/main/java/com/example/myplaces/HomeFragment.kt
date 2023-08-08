package com.example.myplaces

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class HomeFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var textIme:TextView
    lateinit var database:DatabaseReference
    val sharedViewModel:Korisnicko by activityViewModels()
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    //val imageRef=storageRef.child(sharedViewModel.img)
    lateinit var mojaMesta:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home,container,false)
        textIme=view.findViewById(R.id.textViewKorisnikHome)
        auth=FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
        try {
            database = FirebaseDatabase.getInstance().getReference("Users")
            val key = sharedViewModel.ime.replace(".", "").replace("#", "").replace("$", "").replace("[", "").replace("]", "")
            database.child(key).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    textIme.text = snapshot.child("ime").value.toString()
                }
            }.addOnFailureListener { exception ->
                // Handle the exception here
                Log.e(TAG, "Error getting data from Firebase: ${exception.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error accessing Firebase: ${e.message}")
        }
       /* imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()

            // Inicijalizacija ImageView-a
            val imageView: ImageView = view.findViewById(R.id.profilna)

            // Učitavanje slike u ImageView pomoću neke biblioteke za obradu slika, npr. Glide ili Picasso
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        }.addOnFailureListener { exception ->
            // U slučaju greške pri dohvaćanju URL-a
            Log.e(TAG, "Error getting image URL: ${exception.message}")
        }*/
        mojaMesta=view.findViewById(R.id.buttonMojaMesta)
        mojaMesta.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_mojaMestaFragment)
        }
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