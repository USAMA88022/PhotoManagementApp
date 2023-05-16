package com.example.photomanagementapp.Factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photomanagementapp.Repository.ImageRepository
import com.example.photomanagementapp.ViewModel.ImageViewModel


class MediaFactory(val imageRepository: ImageRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepository) as T
    }
}
