package com.example.ftpserver

import android.util.Log
import org.apache.ftpserver.FtpServer
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.listener.ListenerFactory
import org.apache.ftpserver.listener.DefaultListener
import org.apache.ftpserver.filesystem.NativeFileSystemFactory
import java.io.File

class FtpServerService {

    private var ftpServer: FtpServer? = null
    private var serverDirectory: String? = null

    fun startFtpServer(path: String?) {
        serverDirectory = path
        val factory = FtpServerFactory()
        val listenerFactory = ListenerFactory()
        listenerFactory.port = 4444 // Port 4444 for FTP

        // Configure server directory
        val fsFactory = NativeFileSystemFactory()
        fsFactory.rootDirectory = File(path)
        
        val listener = listenerFactory.createListener()
        factory.addListener("default", listener)
        factory.fileSystem = fsFactory
        
        ftpServer = factory.createServer()

        try {
            ftpServer?.start()
        } catch (e: Exception) {
            Log.e("FTP Server", "Error starting FTP server", e)
        }
    }

    fun getServerIp(): String {
        return "192.168.1.5" // Placeholder for local IP address (you should dynamically obtain it in a real scenario)
    }

    fun stopFtpServer() {
        ftpServer?.stop()
    }
}
