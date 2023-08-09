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
import android.widget.Toast
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
    private lateinit var obrisi:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detaljni,container,false)
        naziv=view.findViewById(R.id.editTextNazivMestaU)
        opis=view.findViewById(R.id.editTextKomentarU)
        ocena=view.findViewById(R.id.editTextOcenaU)
        ucitaj=view.findViewById(R.id.progressMestaU)
        azuriraj=view.findViewById(R.id.buttonDodajMestoU)
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
        azuriraj.setOnClickListener{
            val Opis=opis.text.toString()
            val Ocena=ocena.text.toString()
            if(Opis.isNotEmpty()&&Ocena.isNotEmpty()) {

                ucitaj.visibility = View.VISIBLE
                val key = naziv.text.toString().replace(".", "").replace("#", "").replace("$", "")
                    .replace("[", "").replace("]", "")
                val mesto = Places(
                    naziv.text.toString(),
                    opis.text.toString(),
                    ocena.text.toString().toIntOrNull(),
                    sharedViewModel.ime
                )
                database.child(key).setValue(mesto).addOnSuccessListener {

                    ucitaj.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Uspesno ste azurirali mesto ${mesto.naziv}",
                        Toast.LENGTH_SHORT
                    ).show()
                    naziv.text = ""
                    opis.text.clear()
                    ocena.text.clear()
                    findNavController().navigate(R.id.action_detaljniFragment2_to_mojaMestaFragment)


                }.addOnFailureListener {
                    Toast.makeText(context, "Greska", Toast.LENGTH_SHORT)
                }
            }
            else
            {
                Toast.makeText(context,"Niste popunili sva polja", Toast.LENGTH_SHORT)
            }

        }
        obrisi=view.findViewById(R.id.buttonObrisiU)
        obrisi.setOnClickListener{
            database = FirebaseDatabase.getInstance().getReference("Places")
            database.child(naziv.text.toString()).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Uspesno ste obrisali mesto ${naziv.text.toString()}",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_detaljniFragment2_to_mojaMestaFragment)

            }.addOnFailureListener{
                Toast.makeText(context,"Greska",Toast.LENGTH_SHORT).show()
            }



        }



        return view
    }


}