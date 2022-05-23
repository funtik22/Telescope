package ru.a.o.mikhailov.telescope

import android.bluetooth.BluetoothAdapter
import android.content.Context

class BluetoothConnection(private val adapter: BluetoothAdapter, context: Context,val listener: BluetoothConnectThread.Listener) {
    private lateinit  var connectionThread: BluetoothConnectThread
    private val cnt = context
    fun connect(mac: String) {
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {

                connectionThread = BluetoothConnectThread(it, cnt, listener)
                connectionThread.start()
            }
        }
    }

    fun sendMessage(message: String){
        connectionThread.receiveThread.sendMessage(message.toByteArray())
    }
}