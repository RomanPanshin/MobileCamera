package com.example.third_5.ui.theme

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.third_5.R
import com.example.third_5.ui.theme.MyAdapter
import java.io.File

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val data = readTimestamps()
        val adapter = MyAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun readTimestamps(): List<String> {
        val file = File(getExternalFilesDir(null), "photos/date.txt")
        if (!file.exists()) return emptyList()
        val data = file.readLines()
        Log.d("ListActivity", "Data read: $data")
        return data
    }
}
