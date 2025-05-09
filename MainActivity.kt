package com.example.ftpserver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var statusTextView: TextView
    private lateinit var ftpServerService: FtpServerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        statusTextView = findViewById(R.id.statusTextView)

        ftpServerService = FtpServerService()

        startButton.setOnClickListener {
            chooseFolder()
        }
    }

    private val chooseFolderLauncher = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
        uri?.let {
            val path = it.path
            // Trigger FTP Server with chosen folder path
            ftpServerService.startFtpServer(path)
            statusTextView.text = "Server berjalan di: ${ftpServerService.getServerIp()}:4444"
            Toast.makeText(this, "FTP Server dimulai", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseFolder() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        chooseFolderLauncher.launch(intent)
    }
}
