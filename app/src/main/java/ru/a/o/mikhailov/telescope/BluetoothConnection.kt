package ru.a.o.mikhailov.telescope

import android.bluetooth.BluetoothAdapter
import android.content.Context

class BluetoothConnection(private val adapter: BluetoothAdapter, context: Context) {
    lateinit  var connectionThread: BluetoothConnectThread
    private val cnt = context
    fun connect(mac: String) {
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {

                connectionThread = BluetoothConnectThread(it, cnt)
                connectionThread.start()
            }
        }
    }
}