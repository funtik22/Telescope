package ru.a.o.mikhailov.telescope

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val message: MutableLiveData<BluetoothDevice> by lazy{
        MutableLiveData<BluetoothDevice>()
    }
}