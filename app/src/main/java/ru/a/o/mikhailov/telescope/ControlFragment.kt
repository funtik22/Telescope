package ru.a.o.mikhailov.telescope

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.a.o.mikhailov.telescope.databinding.FragmentControlBinding


class ControlFragment : Fragment() {

    lateinit var binding: FragmentControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentControlBinding.inflate(inflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ControlFragment()

    }
}
