package com.example.myplaces

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DodajMestoFragment : Fragment() {

lateinit var database:DatabaseReference
private  val sharedViewModel:Korisnicko by activityViewModels()
lateinit var nazivMesta:EditText
lateinit var opisMesta:EditText
lateinit var ocenaMesta:EditText
lateinit var progress:ProgressBar
lateinit var potvrdi:Button
lateinit var otkazi:Button
lateinit var mesto:Places
lateinit var latituda:EditText
lateinit var longituda:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_dodaj_mesto,container,false)
        nazivMesta=view.findViewById(R.id.editTextNazivMesta)
        opisMesta=view.findViewById(R.id.editTextKomentar)
        ocenaMesta=view.findViewById(R.id.editTextOcena)
        progress=view.findViewById(R.id.progressMesta)
        potvrdi=view.findViewById(R.id.buttonDodajMesto)
        otkazi=view.findViewById(R.id.buttonOtkaziDodavanje)
        latituda=view.findViewById(R.id.editLatituda)
        longituda=view.findViewById(R.id.editLongituda)
        potvrdi.setOnClickListener {
            val nazivPom=nazivMesta.text.toString()
            val opisPom=opisMesta.text.toString()
            val ocenaPom=ocenaMesta.text.toString()
            if(nazivPom.isNotEmpty()&&opisPom.isNotEmpty()&&ocenaPom.isNotEmpty())
            {
                progress.visibility = View.VISIBLE
                database=FirebaseDatabase.getInstance().getReference("Places")
                mesto=Places(nazivMesta.text.toString(),opisMesta.text.toString(),ocenaMesta.text.toString().toIntOrNull(),sharedViewModel.ime,longituda.text.toString(), latituda.text.toString())
                val key = nazivMesta.text.toString().replace(".", "").replace("#", "").replace("$", "").replace("[", "").replace("]", "")
                database.child(key).setValue(mesto).addOnSuccessListener {4
                    progress.visibility=View.GONE
                    Toast.makeText(context, "Uspesno ste dodali mesto ${nazivMesta.text.toString()}", Toast.LENGTH_SHORT).show()
                    nazivMesta.text.clear()
                    opisMesta.text.clear()
                    ocenaMesta.text.clear()


                }.addOnFailureListener{
                    Toast.makeText(context,"Greska",Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                Toast.makeText(context,"Niste popunili sva polja",Toast.LENGTH_SHORT).show()
            }

        }
        otkazi.setOnClickListener{
                findNavController().navigate(R.id.action_dodajMestoFragment_to_homeFragment)
        }

        return view
    }


}