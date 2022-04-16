package ru.a.o.mikhailov.telescope

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.a.o.mikhailov.telescope.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var btAdapter: BluetoothAdapter? = null
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: RcViewDeviceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    private fun init(){
        val btManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        adapter = RcViewDeviceAdapter()
        binding.rcViewDevice.layoutManager = LinearLayoutManager(requireContext())
        binding.rcViewDevice.adapter = adapter
        getPairedDevices()
    }

    @SuppressLint("MissingPermission")
    private fun getPairedDevices(){
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        val tempList = ArrayList<ListItem>()
        pairedDevices?.forEach{
            tempList.add(ListItem(it.name, it.address))
        }
        adapter.submitList(tempList)
    }
}