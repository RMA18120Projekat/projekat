package com.example.myplaces

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DetaljniFragment : Fragment() {

    private  val sharedViewModel:Korisnicko by activityViewModels()
    private lateinit var database:DatabaseReference
    private lateinit var naziv:TextView
    private lateinit var opis:EditText
    private lateinit var ocena:EditText
    private lateinit var azuriraj: Button
    private lateinit var ucitaj:ProgressBar
    private lateinit var back:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detaljni,container,false)
        naziv=view.findViewById(R.id.editTextNazivMestaU)
        opis=view.findViewById(R.id.editTextKomentarU)
        ocena=view.findViewById(R.id.editTextOcenaU)
        ucitaj=view.findViewById(R.id.progressMestaU)
        try {
            ucitaj.visibility=View.VISIBLE
            database = FirebaseDatabase.getInstance().getReference("Places")
            database.child(sharedViewModel.izabranoMesto).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    naziv.text = snapshot.child("naziv").value.toString()
                    opis.hint = snapshot.child("komentar").value.toString()
                    ocena.hint=snapshot.child("ocena").value.toString()
                    ucitaj.visibility=View.GONE
                }
            }.addOnFailureListener { exception ->
                // Handle the exception here
                Log.e(ContentValues.TAG, "Error getting data from Firebase: ${exception.message}")
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error accessing Firebase: ${e.message}")
        }
        back=view.findViewById(R.id.buttonNazadU)
        back.setOnClickListener{
            findNavController().navigate(R.id.action_detaljniFragment2_to_mojaMestaFragment)
        }
        return view
    }


}