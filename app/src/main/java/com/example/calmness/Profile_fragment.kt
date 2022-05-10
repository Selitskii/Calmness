package com.example.calmness

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import kotlin.system.exitProcess

class Profile_fragment : Fragment() {


    lateinit var  edit: Button
    lateinit var  exit: Button
    lateinit var  avatar: ImageView
    lateinit var  name: EditText
    lateinit var  email: EditText
    lateinit var  age: EditText
    lateinit var  weight: EditText
    lateinit var  arteriotony: EditText
    lateinit var  phone: EditText
    lateinit var viewModel: ViewModel

    private lateinit var auth: FirebaseAuth;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.getUser()
        viewModel.takeUser().observe(viewLifecycleOwner, {
            var user: User = it
            name.setText(user.name)
            email.setText(user.email)
            age.setText(user.age)
            weight.setText(user.weight)
            arteriotony.setText(user.arteriotony)
            phone.setText(user.phone)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .override(350,350)
                .into(avatar)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        edit = view.findViewById(R.id.buttonEdit)
        avatar = view.findViewById(R.id.profile_image)
        name = view.findViewById(R.id.editTextTextName)
        email = view.findViewById(R.id.editTextEmail)
        age = view.findViewById(R.id.editTextAge)
        weight = view.findViewById(R.id.editTextWeight)
        arteriotony = view.findViewById(R.id.editTextTextArteriotony)
        phone = view.findViewById(R.id.editTextPhone)
        exit = view.findViewById(R.id.buttonexit)


       // avatar.setImageDrawable(R.drawable.avatar)
        avatar.setOnClickListener {
            getAvatar()
        }
        edit.setOnClickListener {
            viewModel.pushUser(name.text.toString(), currentUser!!.email.toString(),age.text.toString()
                ,weight.text.toString(),arteriotony.text.toString(),phone.text.toString())
        }

        exit.setOnClickListener {
            viewModel.exit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data!=null && data.data!=null){
            if (resultCode == Activity.RESULT_OK)
            {
                Glide
                    .with(this)
                    .load(data.data)
                    .centerCrop()
                    .override(350,350)
                    .into(avatar)

                //avatar.setImageURI(data.data)
                uploadImg(data.data!!)

            }
        }
    }


    private fun getAvatar(){
        val intentChooser = Intent()
        intentChooser.setType("image/*")
        intentChooser.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intentChooser,1)
    }

    fun uploadImg(uri:Uri){
        var imageBitmap: Bitmap
        val imageUri = uri//data?.data as Uri
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, imageUri))
        } else {
            imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
        }
        // val bitmap = (avatar.drawable as BitmapDrawable).bitmap
        val baos :ByteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val byteArray :ByteArray = baos.toByteArray()
        viewModel.saveAvatar(byteArray)
    }
}