package ru.a.o.mikhailov.telescope


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import ru.a.o.mikhailov.telescope.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(), RcViewDeviceAdapter.Listener {

    private var btAdapter: BluetoothAdapter? = null
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: RcViewDeviceAdapter
    private val dataModel: DataModel by activityViewModels()

    private val registerForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            adapter = RcViewDeviceAdapter(this)
            binding.rcViewDevice.layoutManager = LinearLayoutManager(requireContext())
            binding.rcViewDevice.adapter = adapter
            getPairedDevices()
        }
        if (result.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(requireContext(), "Для использование приложения нужен bluetooth",
                Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    @SuppressLint("MissingPermission")
    private fun init(){
        val btManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        if(btAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            registerForResult.launch(enableBtIntent)
        }
        else{
            adapter = RcViewDeviceAdapter(this)
            binding.rcViewDevice.layoutManager = LinearLayoutManager(requireContext())
            binding.rcViewDevice.adapter = adapter
            getPairedDevices()
        }
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


