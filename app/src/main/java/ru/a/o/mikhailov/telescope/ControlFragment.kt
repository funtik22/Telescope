package ru.a.o.mikhailov.telescope


import a.o.mikahilov.myapplication.RepeatListener
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.youtube.player.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import ru.a.o.mikhailov.telescope.databinding.FragmentControlBinding


class ControlFragment : Fragment(), BluetoothConnectThread.Listener {

    private lateinit var binding: FragmentControlBinding
    private val dataModel:DataModel by activityViewModels()
    private lateinit var bluetoothConnection: BluetoothConnection
    private  var bluetoothDevice: BluetoothDevice? = null
    private var handler : Handler? = null
    private lateinit var youtubePlayerView: YouTubePlayerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentControlBinding.inflate(inflater)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        youtubePlayerView = binding.youTubePlayerView
        lifecycle.addObserver(youtubePlayerView)
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
                buttonSendOne.visibility = View.GONE
                buttonSendTwo.visibility = View.GONE
                buttonSendThree.visibility = View.GONE
                buttonSendFour.visibility = View.GONE
                buttonSendOne.setOnTouchListener(RepeatListener(100, 500, View.OnClickListener {
                    bluetoothConnection.sendMessage("2")
                }))
                buttonSendTwo.setOnTouchListener(RepeatListener(100, 500, View.OnClickListener {
                    bluetoothConnection.sendMessage("4")
                }))
                buttonSendThree.setOnTouchListener(RepeatListener(100, 500, View.OnClickListener {
                    bluetoothConnection.sendMessage("1")
                }))
                buttonSendFour.setOnTouchListener(RepeatListener(100, 500, View.OnClickListener {
                    bluetoothConnection.sendMessage("2")
                }))
            }
    }

 /*   override fun onConfigurationChanged(newConfig: Configuration) {
        childFragmentManager.beginTransaction().detach(this).commitAllowingStateLoss()
        super.onConfigurationChanged(newConfig)
        childFragmentManager.beginTransaction().attach(this).commitAllowingStateLoss()
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

            youtubePlayerView.enterFullScreen()
        }
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            youtubePlayerView.exitFullScreen()
        }
    }*/
    private fun init(){

        val btManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        bluetoothConnection = BluetoothConnection(btAdapter, requireContext(), this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ControlFragment()
    }

    override fun onReceive(message: String) {
        if(message == "OK"){
            ContextCompat.getMainExecutor(requireContext()).execute {
                binding.apply {
                    buttonSendOne.visibility = View.VISIBLE
                    buttonSendTwo.visibility = View.VISIBLE
                    buttonSendFour.visibility = View.VISIBLE
                    buttonSendThree.visibility = View.VISIBLE
                }
            }
        }
    }


}
