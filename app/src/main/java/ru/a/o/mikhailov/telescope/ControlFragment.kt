package ru.a.o.mikhailov.telescope


import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ru.a.o.mikhailov.telescope.databinding.FragmentControlBinding


class ControlFragment : Fragment() {

    private lateinit var binding: FragmentControlBinding
    private val dataModel:DataModel by activityViewModels()
    private lateinit var bluetoothConnection: BluetoothConnection
    private  var bluetoothDevice: BluetoothDevice? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentControlBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dataModel.message.observe(this) {
            Log.d("mylog", "name ${it.name}")
            bluetoothDevice = it
        }
        binding.button.setOnClickListener {
            init()
            bluetoothDevice.let {
                bluetoothConnection.connect(it?.mac!!)
            }
        }
        binding.apply {
            buttonSendOne.setOnClickListener {
                bluetoothConnection.sendMessage("1")
                Toast.makeText(requireContext(), "1", Toast.LENGTH_LONG).show()
            }
            buttonSentTwo.setOnClickListener {
                bluetoothConnection.sendMessage("2")
                Toast.makeText(requireContext(), "2", Toast.LENGTH_LONG).show()
            }
            buttonSendThree.setOnClickListener {
                bluetoothConnection.sendMessage("3")
                Toast.makeText(requireContext(), "3", Toast.LENGTH_LONG).show()
            }
            buttonSendFour.setOnClickListener {
                bluetoothConnection.sendMessage("4")
                Toast.makeText(requireContext(), "4", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun init(){
        val btManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        bluetoothConnection = BluetoothConnection(btAdapter, requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = ControlFragment()
    }


}
