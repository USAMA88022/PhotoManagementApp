package com.example.photomanagementapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.photomanagementapp.Adapter.TabLayoutAdapter
import com.example.photomanagementapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding?=null

    var tabTitle= arrayOf("Data Show", "Book Mark")

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context),container,false)


        val allTabs = binding?.tabLayout
        val myPager =binding?.viewpager

        myPager?.adapter=TabLayoutAdapter(activity?.supportFragmentManager!!,lifecycle)
        TabLayoutMediator(allTabs!!,myPager!!){
            tab,position->
            tab.text=tabTitle[position]
        }.attach()
        return binding?.root
    }
    //check permission
    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
            }
        }
    }

}