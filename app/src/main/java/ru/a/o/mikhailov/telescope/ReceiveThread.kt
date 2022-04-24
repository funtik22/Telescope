package ru.a.o.mikhailov.telescope

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ReceiveThread(private val bSocket: BluetoothSocket): Thread() {
    private var inStream: InputStream? = null
    private var outStream: OutputStream? = null
    init{
        try{
            inStream = bSocket.inputStream
        }catch (i: IOException){

        }
        try{
            outStream = bSocket.outputStream
        }catch (i: IOException){

        }
    }

    override fun run() {
        val buffer = ByteArray(2)
        while(true){
            try{
                val size = inStream?.read(buffer)
                val message = String(buffer, 0, size!!)
                Log.d("mylog", message)
            }catch(i : IOException){
                break
            }
        }
    }

    fun sendMessage(byteArray: ByteArray){
        try{
            outStream?.write(byteArray)
        }catch (i: IOException){

        }
    }
}