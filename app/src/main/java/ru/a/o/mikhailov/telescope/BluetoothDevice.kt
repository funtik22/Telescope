package ru.a.o.mikhailov.telescope

import java.io.Serializable

data class BluetoothDevice(
    var name: String,
    var mac: String
):Serializable
