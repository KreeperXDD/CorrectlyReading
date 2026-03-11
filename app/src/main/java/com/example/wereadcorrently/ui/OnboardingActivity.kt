package com.example.wereadcorrently.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wereadcorrently.R
import com.example.wereadcorrently.data.Settings
import com.example.wereadcorrently.data.SettingsManager
import com.example.wereadcorrently.databinding.ActivityOnboardingBinding
import com.example.wereadcorrently.service.CameraHelper
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var cameraHelper: CameraHelper
    private lateinit var setupAdapter: SetupPagerAdapter

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCameraInFirstFragment()
        } else {
            // Если разрешение не дано, можно скрыть PreviewView или показать сообщение
            // В первом фрагменте нужно обработать отсутствие разрешения
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        binding.skipButton.setOnClickListener {
            showQuickSetup()
        }

        binding.quickSetupContainer.visibility = View.GONE
    }

    private fun setupViewPager() {
        val adapter = OnboardingPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
            }
        })
    }

    public fun showQuickSetup() {
        binding.viewPager.visibility = View.GONE
        binding.skipButton.visibility = View.GONE
        binding.quickSetupContainer.visibility = View.VISIBLE

        setupAdapter = SetupPagerAdapter(this)
        binding.setupViewPager.adapter = setupAdapter
        TabLayoutMediator(binding.tabDots, binding.setupViewPager) { tab, position ->

        }.attach()

        binding.setupViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.finishButton.visibility = if (position == 2) View.VISIBLE else View.GONE
            }
        })

        binding.finishButton.setOnClickListener {
            saveAllSettings()
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            startCameraInFirstFragment()
        } else {
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun startCameraInFirstFragment() {
        val fragment = supportFragmentManager.findFragmentByTag("f0") as? SetupCameraFragment
        fragment?.let {
            val previewView = it.requireView().findViewById<PreviewView>(R.id.previewView)
            previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE

            cameraHelper = CameraHelper()
            cameraHelper.startPreview(
                previewView = previewView,
                lifecycleOwner = this,
                cameraLens = CameraSelector.LENS_FACING_FRONT
            )
        }
    }

    private fun saveAllSettings() {
        val notificationsFrag = supportFragmentManager.findFragmentByTag("f1") as? SetupNotificationsFragment
        val locationFrag = supportFragmentManager.findFragmentByTag("f2") as? SetupLocationFragment

        val vibration = notificationsFrag?.isVibrationEnabled() ?: true
        val sound = notificationsFrag?.isSoundEnabled() ?: true
        val both = notificationsFrag?.isBothEnabled() ?: false
        val location = locationFrag?.getSelectedLocation() ?: "unknown"

        val alertEnabled = vibration || sound || both

        val settings = Settings(
            minDistance = 30f,
            maxAngle = 15f,
            alertEnabled = alertEnabled,
            soundEnabled = sound,
            vibrationEnabled = vibration,
            cameraId = 0
        )

        SettingsManager.saveSettings(settings)
        setResult(RESULT_OK)
        finish()
    }

    inner class OnboardingPagerAdapter(activity: AppCompatActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = 3
        override fun createFragment(position: Int) = OnboardingPageFragment.newInstance(position)
    }

    inner class SetupPagerAdapter(activity: AppCompatActivity) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = 3
        override fun createFragment(position: Int) = when (position) {
            0 -> SetupCameraFragment()
            1 -> SetupNotificationsFragment()
            2 -> SetupLocationFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}