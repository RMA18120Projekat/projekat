package com.example.myplaces

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text


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
lateinit var set:Button
////////////////////////////////////////
lateinit var teren:Spinner

 var terenIzabran:String="Fudbalski"
/////////////////////////////////////////
lateinit var sirinaObruca:Spinner
lateinit var sirinaText:TextView
private  var sirinaIzabrana:String="Normlna sirina"
lateinit var osobinaObruca:Spinner
lateinit var osobinaText:TextView
 var osobinaIzabrana:String="Normalan osecaj"
lateinit var podlogaKosarka:Spinner
lateinit var podlogaKText:TextView
 var podlogaKIzabrana:String="Guma"
lateinit var mrezica:Spinner
lateinit var mrezicaText:TextView
var mrezicaIzabrana:String="Ima"
lateinit var kosevi:Spinner
lateinit var koseviText:TextView
 var koseviIzabrana:String="305cm"
///////////////////////////////////////
lateinit var posecenost:Spinner
 var posecenostIzabrana:String="Mala"

lateinit var dimenzije:Spinner
var dimenzijeIzabrana:String="Male"
//////////////////////////////////////
lateinit var mreza:Spinner
lateinit var mrezaText:TextView
 var mrezaIzabrana:String="Ima"
lateinit var golovi:Spinner
lateinit var goloviText:TextView
 var goloviIzabrana:String="Manji"
lateinit var podlogaFudbal:Spinner
lateinit var podlogaFText:TextView
 var podlogaFIzabrana:String="Trava"
////////////////////////////////
private val locationViewModel:LocationViewModel by activityViewModels()
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
        teren=view.findViewById(R.id.spinnerTeren)
        sirinaObruca=view.findViewById(R.id.spinnerSirina)
        sirinaText=view.findViewById(R.id.textSirina)
        osobinaObruca=view.findViewById(R.id.spinnerOsobina)
        osobinaText=view.findViewById(R.id.textOsobina)
        podlogaKosarka=view.findViewById(R.id.spinnerPodlogaKosarka)
        podlogaKText=view.findViewById(R.id.textPodlogaKosarka)
        mrezica=view.findViewById(R.id.spinnerMrezica)
        mrezicaText=view.findViewById(R.id.textMrezica)
        mreza=view.findViewById(R.id.spinnerMreza)
        posecenost=view.findViewById(R.id.spinnerPosecenost)
        dimenzije=view.findViewById(R.id.spinnerDimenzije)
        mrezaText=view.findViewById(R.id.textMreza)
        golovi=view.findViewById(R.id.spinnerGolovi)
        goloviText=view.findViewById(R.id.textGolovi)
        podlogaFudbal=view.findViewById(R.id.spinnerPodlogaFudbal)
        podlogaFText=view.findViewById(R.id.textPodlogaFudbal)
        kosevi=view.findViewById(R.id.spinnerKosevi)
        koseviText=view.findViewById(R.id.textKosevi)

        ////////////////////////////////////////////////////////////////////////////////////////
        teren.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, positon: Int, id: Long) {
                 terenIzabran=adapterView?.getItemAtPosition(positon).toString()
                Toast.makeText(context,"${terenIzabran}",Toast.LENGTH_SHORT).show()
                if(terenIzabran=="Fudbalski")
                {
                    mreza.visibility=View.VISIBLE
                   golovi.visibility=View.VISIBLE
                    podlogaFudbal.visibility=View.VISIBLE
                    mrezaText.visibility=View.VISIBLE
                    goloviText.visibility=View.VISIBLE
                    podlogaFText.visibility=View.VISIBLE
                    sirinaObruca.visibility=View.GONE
                    sirinaText.visibility=View.GONE
                    osobinaObruca.visibility=View.GONE
                    osobinaText.visibility=View.GONE
                    podlogaKosarka.visibility=View.GONE
                    podlogaKText.visibility=View.GONE
                    mrezica.visibility=View.GONE
                    mrezicaText.visibility=View.GONE
                    kosevi.visibility=View.GONE
                    koseviText.visibility=View.GONE

                }
                if(terenIzabran=="Kosarkaski")
                {
                    mreza.visibility=View.GONE
                    golovi.visibility=View.GONE
                    podlogaFudbal.visibility=View.GONE
                    mrezaText.visibility=View.GONE
                    goloviText.visibility=View.GONE
                    podlogaFText.visibility=View.GONE
                    sirinaObruca.visibility=View.VISIBLE
                    sirinaText.visibility=View.VISIBLE
                    osobinaObruca.visibility=View.VISIBLE
                    osobinaText.visibility=View.VISIBLE
                    podlogaKosarka.visibility=View.VISIBLE
                    podlogaKText.visibility=View.VISIBLE
                    mrezica.visibility=View.VISIBLE
                    mrezicaText.visibility=View.VISIBLE
                    kosevi.visibility=View.VISIBLE
                    koseviText.visibility=View.VISIBLE


                }

            }
        }
        sirinaObruca.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                sirinaIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
            osobinaObruca.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    positon: Int,
                    id: Long
                ) {
                    osobinaIzabrana = adapterView?.getItemAtPosition(positon).toString()
                }
            }
        podlogaKosarka.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                podlogaKIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
        mrezica.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                mrezicaIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
        posecenost.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                posecenostIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
       dimenzije.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                dimenzijeIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
        mreza.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                mrezaIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
        golovi.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                goloviIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
        podlogaFudbal.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                podlogaFIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }
        kosevi.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                positon: Int,
                id: Long
            ) {
                koseviIzabrana = adapterView?.getItemAtPosition(positon).toString()
            }
        }









////////////////////////////////////////////////////////////////
        val lonObserver= Observer<String>{newValue->
            longituda.setText(newValue.toString())
            sharedViewModel.longituda=longituda.text.toString()


        }
        locationViewModel.longitude.observe(viewLifecycleOwner,lonObserver)
        val latiObserver= Observer<String>{newValue->
            latituda.setText(newValue.toString())
            sharedViewModel.latituda=latituda.text.toString()

        }
        locationViewModel.latitude.observe(viewLifecycleOwner,latiObserver)
        set=view.findViewById(R.id.buttonSet)
        set.setOnClickListener{
            locationViewModel.setLocation=true

            findNavController().navigate(R.id.action_dodajMestoFragment_to_mapFragment)
        }
        ///////////////////////////////////////////////////////////////////////////////
        potvrdi.setOnClickListener {
            val nazivPom=nazivMesta.text.toString()
            val opisPom=opisMesta.text.toString()
            val ocenaPom=ocenaMesta.text.toString()
            if(nazivPom.isNotEmpty()&&opisPom.isNotEmpty()&&ocenaPom.isNotEmpty())
            {
                progress.visibility = View.VISIBLE
                database=FirebaseDatabase.getInstance().getReference("Places")
                mesto = if(terenIzabran=="Fudbalski") {
                    Places(nazivMesta.text.toString(),opisMesta.text.toString(),ocenaMesta.text.toString().toIntOrNull(),sharedViewModel.ime,longituda.text.toString(), latituda.text.toString(),terenIzabran,"","","","","",posecenostIzabrana,dimenzijeIzabrana,mrezaIzabrana,goloviIzabrana,podlogaFIzabrana)

                } else {
                    Places(nazivMesta.text.toString(),opisMesta.text.toString(),ocenaMesta.text.toString().toIntOrNull(),sharedViewModel.ime,longituda.text.toString(), latituda.text.toString(),terenIzabran,sirinaIzabrana,osobinaIzabrana,podlogaKIzabrana,koseviIzabrana,mrezicaIzabrana,posecenostIzabrana,dimenzijeIzabrana,"","","")
                }

                val key = nazivMesta.text.toString().replace(".", "").replace("#", "").replace("$", "").replace("[", "").replace("]", "")
                database.child(key).setValue(mesto).addOnSuccessListener {4
                    progress.visibility=View.GONE
                    Toast.makeText(context, "Uspesno ste dodali mesto ${nazivMesta.text.toString()}", Toast.LENGTH_SHORT).show()
                    nazivMesta.text.clear()
                    opisMesta.text.clear()
                    ocenaMesta.text.clear()
                    longituda.text.clear()
                    latituda.text.clear()


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