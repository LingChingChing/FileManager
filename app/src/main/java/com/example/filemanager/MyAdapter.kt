package com.example.filemanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.nio.file.Files

class MyAdapter(var context: Context, var filesAndFolders: Array<File>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        lateinit var textView:TextView
        lateinit var imageView: ImageView
        fun ViewHolder(itemView: View){
            super.itemView
            textView=itemView.findViewById(R.id.file_name_text_view)
            imageView=itemView.findViewById(R.id.icon_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
        var view:View = LayoutInflater.from(context).inflate(R.layout.recycle_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return filesAndFolders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
        var selectedFile:File = filesAndFolders[position]
        holder.textView.text = selectedFile.name
        if (selectedFile.isDirectory()){
            holder.imageView.setImageResource(R.drawable.baseline_folder_24)
        } else{
            holder.imageView.setImageResource(R.drawable.baseline_insert_drive_file_24)
        }
        holder.itemView.setOnClickListener{
            fun onClick(v: View){
                if(selectedFile.isDirectory()){
                    var intent: Intent = Intent(context, FileListActivity::class.java)
                    var path: String = selectedFile.absolutePath
                    intent.putExtra("path", path)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }else{
                    try {
                        var intent: Intent = Intent()
                        intent.action = android.content.Intent.ACTION_VIEW
                        val type: String = "image/*"
                        intent.setDataAndType(Uri.parse(selectedFile.absolutePath), type)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }catch(e:Exception){
                        Toast.makeText(context.applicationContext,"Cannot open the file",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        holder.itemView.setOnLongClickListener { v ->
            val popupMenu = PopupMenu(context, v)
            popupMenu.menu.add("DELETE")
            popupMenu.menu.add("MOVE")
            popupMenu.menu.add("RENAME")
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "DELETE" -> {
                        val deleted: Boolean = selectedFile.delete()
                        if (deleted) {
                            Toast.makeText(context.applicationContext, "DELETED", Toast.LENGTH_SHORT).show()
                            v.visibility = View.GONE
                        }
                        true
                    }
                    "MOVE" -> {
                        Toast.makeText(context.applicationContext, "MOVE", Toast.LENGTH_SHORT).show()
                        true
                    }
                    "RENAME" -> {
                        Toast.makeText(context.applicationContext, "RENAME", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            true
        }

    }
}