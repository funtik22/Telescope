package ru.a.o.mikhailov.telescope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import ru.a.o.mikhailov.telescope.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Telescope)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            open.setOnClickListener {
                drawer.openDrawer(GravityCompat.START)
            }
            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.settings -> {
                        Toast.makeText(this@MainActivity, "item1", Toast.LENGTH_SHORT).show()
                        //
                        //
                    }
                    R.id.control -> {
                        //
                        //
                        //
                    }
                }
                true

            }
        }
    }
}