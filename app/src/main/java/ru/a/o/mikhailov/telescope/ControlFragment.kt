package ru.a.o.mikhailov.telescope

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.a.o.mikhailov.telescope.databinding.FragmentControlBinding


class ControlFragment : Fragment() {

    private lateinit var binding: FragmentControlBinding
    private val dataModel:DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentControlBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dataModel.message.observe(this) {
            Log.d("mylog", "name ${it.name}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ControlFragment()
    }


}
