package com.example.wereadcorrently

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wereadcorrently.data.SettingsManager
import com.example.wereadcorrently.databinding.ActivityMainBinding
import com.example.wereadcorrently.ui.OnboardingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Запуск OnboardingActivity и получение результата
    private val onboardingLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            SettingsManager.setOnboardingCompleted()
            // После успешного онбординга продолжаем загрузку основного интерфейса
            setupNavigation()
        } else {
            // Если онбординг не завершён (например, нажали назад), можно закрыть приложение
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Проверяем первый запуск
        if (SettingsManager.isFirstLaunch()) {
            startOnboarding()
        } else {
            setupNavigation()
        }
    }

    private fun startOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        onboardingLauncher.launch(intent)
    }

    private fun setupNavigation() {
        // Настройка нижней навигации и NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }
}