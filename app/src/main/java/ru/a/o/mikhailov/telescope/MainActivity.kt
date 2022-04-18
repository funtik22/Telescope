package ru.a.o.mikhailov.telescope


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import ru.a.o.mikhailov.telescope.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_Telescope)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, SettingsFragment.newInstance())
            .commit()

        binding.apply {
            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.settings -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.place_holder, SettingsFragment.newInstance())
                            .commit()
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    R.id.control -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.place_holder, ControlFragment.newInstance())
                            .commit()
                        drawer.closeDrawer(GravityCompat.START)
                    }
                }
                true
            }
        }
    }

}