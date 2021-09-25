package com.example.hacksai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.hacksai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        this.binding = binding

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        setSupportActionBar(binding.toolbar)

        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            title = when (destination.id) {
                R.id.pirates -> supportActionBar?.setTitle("Браконьеры").toString()
                R.id.sailors -> supportActionBar?.setTitle("Легальные судна").toString()
                else -> supportActionBar?.setTitle("NORI").toString()
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigate(it.findNavController().graph.startDestinationId)
        }
    }
}