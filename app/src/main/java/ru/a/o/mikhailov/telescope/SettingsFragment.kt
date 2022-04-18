package ru.a.o.mikhailov.telescope

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import ru.a.o.mikhailov.telescope.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(), RcViewDeviceAdapter.Listener {

    private var btAdapter: BluetoothAdapter? = null
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: RcViewDeviceAdapter
    private val dataModel: DataModel by activityViewModels()

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
        const val DEVICE_KEY = "device key"
    }

    private fun init(){
        val btManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        adapter = RcViewDeviceAdapter(this)
        binding.rcViewDevice.layoutManager = LinearLayoutManager(requireContext())
        binding.rcViewDevice.adapter = adapter
        getPairedDevices()
    }

    @SuppressLint("MissingPermission")
    private fun getPairedDevices(){
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        val tempList = ArrayList<ru.a.o.mikhailov.telescope.BluetoothDevice>()
        pairedDevices?.forEach{
            tempList.add(BluetoothDevice(it.name, it.address))
        }
        adapter.submitList(tempList)
    }

    override fun onClick(item: ru.a.o.mikhailov.telescope.BluetoothDevice) {
        dataModel.message.value = item
        activity?.supportFragmentManager?.beginTransaction()
            ?.remove(this)
            ?.replace(R.id.place_holder, ControlFragment.newInstance())
            ?.commit()
    }
}


