package com.example.photomanagementapp.interfaces

import android.view.View
import com.example.photomanagementapp.DataModel.imagesModel
import com.example.photomanagementapp.DataModel.Bookmark

interface onClickinterface {
   fun onImageClick(imageModel:imagesModel)
   fun PopUpClick(imageModel: imagesModel, myView: View)
   fun onFavClick(bookmark: Bookmark)
}