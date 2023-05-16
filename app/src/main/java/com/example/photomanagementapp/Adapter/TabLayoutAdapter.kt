package com.example.photomanagementapp.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photomanagementapp.ui.AddBookmark
import com.example.photomanagementapp.ui.ImageFragment

class TabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return ImageFragment()
            1-> return AddBookmark()
        }
        return ImageFragment()
    }

}