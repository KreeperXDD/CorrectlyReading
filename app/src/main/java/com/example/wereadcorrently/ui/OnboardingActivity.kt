package com.example.wereadcorrently.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wereadcorrently.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wereadcorrently.ui.OnboardingPageFragment
import com.example.wereadcorrently.data.Settings
import com.example.wereadcorrently.data.SettingsManager
import com.example.wereadcorrently.databinding.ActivityOnboardingBinding
import com.example.wereadcorrently.service.CameraHelper

class OnboardingActivity() : AppCompatActivity()
{
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var cameraHelper: CameraHelper

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
        isGranted ->
        if(isGranted){
            cameraHelper = CameraHelper()
            cameraHelper.startPreviw(binding.previewView.surfaceProvider, this)
        }else
        {
            binding.previewView.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        binding.skipButton.setOnClickListener {
            showQuickStep()
        }

        binding.quickSetupContainer.visibility = View.GONE
    }

    public fun setupViewPager() {
        val adapter = OnboardingPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == adapter.itemCount - 1 ) showQuickStep()
            }
        })
    }

    public fun showQuickStep() {
        binding.viewPager.visibility = View.GONE
        binding.skipButton.visibility = View.GONE
        binding.quickSetupContainer.visibility = View.GONE

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ){
            cameraHelper = CameraHelper()
            cameraHelper.startPreviw(binding.previewView.surfaceProvider, this)
        }else{
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        binding.continueButton.setOnClickListener { saveQuickSettings() }
    }

    private fun saveQuickSettings() {
        val settings = Settings(
            minDistance = 30f,
            maxAngle = 15f,
            alertEnable = binding.checkboxAlert.isChecked,
            soundEnabled = binding.checkboxSound.isChecked,
            vibrationEnabled = binding.checkboxVibration.isChecked,
            cameraId = when (binding.radioGroupCamera.checkedRadioButtonId) {
                R.id.radioFront -> 0
                R.id.radioBack -> 1
                else -> 0
            }
        )
        SettingsManager.saveSettings(settings)
        setResult(RESULT_OK)
        finish()
    }

    inner class OnboardingPagerAdapter(activity: OnboardingActivity):
            FragmentStateAdapter(activity){
        override fun getItemCount() = 3
        override fun createFragment(position: Int) = OnboardingPageFragment.newInstance(position)
    }
}

