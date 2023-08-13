package com.example.myplaces

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


class EditFragment : Fragment() {
    lateinit var pass: EditText
    lateinit var passC:EditText
    lateinit var passN:EditText
    lateinit var korisnickoIme: EditText
    lateinit var ime: EditText
    lateinit var prezime: EditText
    lateinit var brojTelefona: EditText
    lateinit var progress: ProgressBar
    lateinit var dugme: Button
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var imgUrl:String
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var storageRef: StorageReference
    lateinit var user:User
    private  val sharedViewModel:Korisnicko by activityViewModels()
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001

    private lateinit var openCameraButton: Button
    private lateinit var imageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_edit, container, false)
        //INICIJALIZACIJA
        pass=view.findViewById(R.id.editTextStaraSifraU)
        passN=view.findViewById(R.id.editTextNovaSifraU)
        passC=view.findViewById(R.id.editTextNovaSifraU2)
        korisnickoIme=view.findViewById(R.id.editTextMejlU)
        korisnickoIme.setText(sharedViewModel.user.korisnicko)
        ime=view.findViewById(R.id.editTextImeU)
        ime.setText(sharedViewModel.user.ime)
        prezime=view.findViewById(R.id.editTextPrezimeU)
        prezime.setText(sharedViewModel.user.prezime)
        brojTelefona=view.findViewById(R.id.editTextBrojTelefonaU)
        brojTelefona.setText(sharedViewModel.user.brojTelefona.toString())
        progress=view.findViewById(R.id.progressBar1U)
        dugme=view.findViewById(R.id.buttonRegU)
        auth=FirebaseAuth.getInstance()
        openCameraButton = view.findViewById(R.id.buttonPhotoU)
        imageView = view.findViewById(R.id.imageView6U)
        if(sharedViewModel.user.img!="")
        {
            Glide.with(requireContext())
                .load(sharedViewModel.user.img)
                .into(imageView)
        }
        storageRef = FirebaseStorage.getInstance().reference
        // KLIK NA DUGME I UZ DOZVOLU POKRETANJE KAMERE
        openCameraButton.setOnClickListener{
            if (checkCameraPermission()) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            } else {
                // Ako dozvola nije odobrena, zahtevajte je
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
        database = FirebaseDatabase.getInstance().getReference("Users")
        //KLIK NA AZURIRAJ DUGME AZURIRA PODATKE U BAZI, UPISUJE IH U SHARED VIEW MODEL I SALJE KORISNIKA NA FRAGMENT LICNE INFORMACIJE
        dugme.setOnClickListener {
            progress.visibility = View.VISIBLE
            val korisnicko = korisnickoIme.text.toString()
            val sifra = pass.text.toString()
            val newSifra=passN.text.toString()
            val newSifraC=passC.text.toString()
            val name = ime.text.toString()
            val surname = prezime.text.toString()
            val numberPhone = brojTelefona.text.toString()
            if(korisnicko==sharedViewModel.user.korisnicko&&newSifra.isEmpty()&&sifra==sharedViewModel.user.sifra)
            {
                user=User(korisnicko,pass.text.toString(),ime.text.toString(),prezime.text.toString(),brojTelefona.text.toString().toLongOrNull(),imgUrl)
                val key = korisnicko.replace(".", "").replace("#", "").replace("$", "").replace("[", "").replace("]", "")
                val userUpdates = mapOf(
                    key to mapOf(
                        "korisnicko" to user.korisnicko,
                        "pass" to user.sifra,
                        "ime" to user.ime,
                        "prezime" to user.prezime,
                        "brojTelefona" to user.brojTelefona,
                        "img" to user.img
                    )
                )
                database.updateChildren(userUpdates).addOnSuccessListener {
                    progress.visibility=View.GONE
                    sharedViewModel.ime=korisnicko
                    korisnickoIme.text.clear()
                    prezime.text.clear()
                    pass.text.clear()
                    brojTelefona.text.clear()
                    ime.text.clear()
                    Toast.makeText(context,"Uspesno azuriranje",Toast.LENGTH_LONG)
                    findNavController().navigate(R.id.action_editFragment_to_infoFragment)

                }.addOnFailureListener{
                    progress.visibility=View.GONE

                    Toast.makeText(context,"Greska",Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                if(pass.text.toString().isEmpty()||pass.text.toString()!=user.sifra.toString())
                {
                    progress.visibility=View.GONE

                    Toast.makeText(context,"Nalog ne moze biti azuriran sa netacnom trenutnom sifrom",Toast.LENGTH_SHORT).show()
                }
                else if(newSifra!=newSifraC)
                {
                    progress.visibility=View.GONE

                    Toast.makeText(context,"Uneta nova sifra nije potvrdjena",Toast.LENGTH_SHORT).show()
                }

            }


        }



        return view
    }
    private fun checkCameraPermission() : Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            // Create a reference to the image file in Firebase Storage
            val imagesRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")

            // Convert the bitmap to bytes
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()

            // Upload the image to Firebase Storage
            val uploadTask = imagesRef.putBytes(imageData)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Image upload success
                    // Now you can get the download URL of the image and save it to the database
                    imagesRef.downloadUrl.addOnSuccessListener { uri ->
                        // Save the URI to the database or use it as needed
                        imgUrl = uri.toString()
                        sharedViewModel.img=imgUrl
                        // Add the code to save the URL to the user's data in Firebase Database here
                    }.addOnFailureListener { exception ->
                        // Handle any errors that may occur while retrieving the download URL
                        Toast.makeText(requireContext(), "Failed to get download URL.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Image upload failed
                    val errorMessage = task.exception?.message
                    Toast.makeText(requireContext(), "Image upload failed. Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}