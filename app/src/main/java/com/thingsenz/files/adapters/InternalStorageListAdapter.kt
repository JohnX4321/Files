package com.thingsenz.files.adapters

import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thingsenz.files.R
import com.thingsenz.files.models.InternalStorageFilesModel
import java.io.File

class InternalStorageListAdapter(val internalStorageFilesModelList: List<InternalStorageFilesModel>): RecyclerView.Adapter<InternalStorageListAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val lblFileName = view.findViewById<TextView>(R.id.file_name)
        val imgItemIcon=view.findViewById<ImageView>(R.id.icon);
        val checkBox=view.findViewById<CheckBox>(R.id.checkBox)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.media_list_item_view,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val esfm=internalStorageFilesModelList[position]
        holder.lblFileName.text=esfm.fileName
        val ext=esfm.fileName.substring(esfm.fileName.lastIndexOf(".")+1)
        val file= File(esfm.filePath)
        if (file.isDirectory)
            holder.imgItemIcon.setImageResource(R.drawable.ic_folder)
        else if (ext=="png"||ext=="jpg"||ext=="jpeg") {
            if (file.exists()) {
                holder.imgItemIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(esfm.filePath),64,64))
            }
        } else if (ext=="pdf"||ext=="opus")
            holder.imgItemIcon.setImageResource(R.drawable.ic_pdf_file)
        else if (ext=="mp3"||ext=="aac"||ext=="wav"||ext=="m4a")
            holder.imgItemIcon.setImageResource(R.drawable.ic_audio_file)
        else if (ext=="txt")
            holder.imgItemIcon.setImageResource(R.drawable.ic_text_file)
        else if (ext=="zip"||ext=="rar"||ext=="7z")
            holder.imgItemIcon.setImageResource(R.drawable.ic_zip_folder)
        else if (ext=="html"||ext=="xml")
            holder.imgItemIcon.setImageResource(R.drawable.ic_html_file)
        else if (ext=="mp4"||ext=="3gp"||ext=="avi"||ext=="wmv")
            holder.imgItemIcon.setImageResource(android.R.drawable.presence_video_away) //update
        else if (ext=="apk")
            holder.imgItemIcon.setImageResource(R.drawable.ic_apk)
        else holder.imgItemIcon.setImageResource(R.drawable.ic_un_supported_file)
        if (esfm.isCheckboxVisible) holder.checkBox.visibility= View.VISIBLE
        else holder.checkBox.visibility= View.GONE
        holder.checkBox.isChecked = esfm.isSelected
    }

    override fun getItemCount(): Int {
        return internalStorageFilesModelList.size
    }


}