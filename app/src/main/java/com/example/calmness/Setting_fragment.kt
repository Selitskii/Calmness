package com.example.calmness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class Setting_fragment : Fragment() {

   lateinit var btAbout : Button
    lateinit var btHelper : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.setting_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btAbout = view.findViewById(R.id.buttonAbout)
        btHelper = view.findViewById(R.id.buttonManual)
        btAbout.setOnClickListener{
            val intent:Intent = Intent(activity,About_Activity::class.java)
            startActivity(intent)
        }
        btHelper.setOnClickListener {
            val intent:Intent = Intent(activity,HelperActivity::class.java)
            startActivity(intent)
        }
    }

}