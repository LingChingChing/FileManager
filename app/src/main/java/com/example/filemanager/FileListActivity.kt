package com.example.filemanager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileListActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)

        var recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        var noFilesText: TextView = findViewById(R.id.nofiles_textview)

        var path: String? = intent.getStringExtra("path")
        var root: File = File(path)
        var filesAndFolders: Array<File> = root.listFiles()

        if (filesAndFolders == null || filesAndFolders.size == 0) {
            noFilesText.visibility = View.VISIBLE
            return;
        }

        noFilesText.visibility = View.VISIBLE

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(applicationContext,filesAndFolders)
    }
}