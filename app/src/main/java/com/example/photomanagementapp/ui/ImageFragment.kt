package com.example.photomanagementapp.ui
import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photomanagementapp.Adapter.ImageAdapter
import com.example.photomanagementapp.DataModel.imagesModel
import com.example.photomanagementapp.Factory.MediaFactory
import com.example.photomanagementapp.R
import com.example.photomanagementapp.Repository.ImageRepository
import com.example.photomanagementapp.ViewModel.ImageViewModel
import com.example.photomanagementapp.databinding.DialogRenameBinding
import com.example.photomanagementapp.databinding.FragmentImageBinding
import com.example.photomanagementapp.db.AppDB
import com.example.photomanagementapp.DataModel.Bookmark
import com.example.photomanagementapp.interfaces.onClickinterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class ImageFragment : Fragment(),onClickinterface {
    private var binding: FragmentImageBinding?=null
    private var recyclerView: RecyclerView?=null
    private var imageViewModel:ImageViewModel?=null
    private var popupMenu: PopupMenu?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentImageBinding.inflate(LayoutInflater.from(context),container,false)
        recyclerView = binding?.recyclerView

        val db = AppDB.getDatabase(requireContext())
        val imageRepository= ImageRepository(requireContext(),db)
        imageViewModel= ViewModelProvider(this,MediaFactory(imageRepository)).get(ImageViewModel::class.java)
        imageViewModel?.getImages()
        recyclerView?.layoutManager= GridLayoutManager(requireContext(),2)
        imageViewModel!!.getImagesLiveData().observe(requireActivity()){
        recyclerView?.adapter =ImageAdapter(requireContext(),it, this)

        }

        setHasOptionsMenu(true)
        return binding?.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_menu,menu)

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.gridView){

            recyclerView?.layoutManager= GridLayoutManager(requireContext(),2)
        }
        if(id==R.id.linearView){

            recyclerView?.layoutManager= LinearLayoutManager(requireContext())

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onImageClick(imageModel: imagesModel) {
        TODO("Not yet implemented")
    }

    override fun PopUpClick(imageModel: imagesModel, myView: View) {
        popUpMenu(myView,imageModel)
    }

    override fun onFavClick(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun popUpMenu(imageview: View, imageModel:imagesModel){

        popupMenu = PopupMenu(imageview.context,imageview)
        popupMenu!!.inflate(R.menu.show_menu)
        popupMenu!!.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.share->{
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "image/*"
                    try {
                        val imageUri = Uri.parse(imageModel.uri)
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                        startActivity(Intent.createChooser(shareIntent, "Share Image"))
                    } catch (e: Exception) {
                        // Handle any exceptions that may occur
                        Toast.makeText(requireContext(), "Failed to share image", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                    true

                }
                R.id.rename->{
                requestPermission()
                    val customDialog = LayoutInflater.from(context).inflate(R.layout.dialog_rename, null)
                    val bind = DialogRenameBinding.bind(customDialog)
                    val dialog = context?.let { it1 ->
                        MaterialAlertDialogBuilder(it1).setView(customDialog)
                            .setCancelable(false)
                            .setPositiveButton("Rename Image") { self, _ ->
                                self.dismiss()

                                val currentFile = File(imageModel.uri.toString())
                                val newName = bind.renameField.text.toString().trim()

                                if (newName.isNotEmpty() && currentFile.exists()) {
                                    val currentExtension = currentFile.extension
                                    val newFilePath = "${currentFile.parent}/$newName.$currentExtension"
                                    val newFile = File(newFilePath)

                                    if (currentFile.renameTo(newFile)) {
                                        Toast.makeText(context, "File renamed successfully", Toast.LENGTH_SHORT)
                                            .show()
                                        MediaScannerConnection.scanFile(
                                            context,
                                            arrayOf(newFile.toString()),
                                            arrayOf("images/*"),
                                            null
                                        )
                                        imageModel.name = newFile.name
                                        imageModel.uri = newFile.path
                                    } else {
                                        Toast.makeText(context, "Failed to rename file", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    Toast.makeText(context, "Please enter a valid name", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("Cancel") { self, _ ->
                                self.dismiss()
                            }
                            .create()
                    }
                    dialog?.show()
                    bind.renameField.text = SpannableStringBuilder(imageModel.name)


                    true
                }

                R.id.bookmark->{
                    try{
                        imageViewModel?.insertData( imageModel.name, imageModel.uri)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    true
                }
                else->{
                    false
                }
            }
        }
        popupMenu!!.show()
    }
    // for Requesting 11 or Higher Storage Permission
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent =
                    Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse("package:${requireContext().applicationContext.packageName}")
                ContextCompat.startActivity(requireContext(), intent, null)
            }
        }
    }
}