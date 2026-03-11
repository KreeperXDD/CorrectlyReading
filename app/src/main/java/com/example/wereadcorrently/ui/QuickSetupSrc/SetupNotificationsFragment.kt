package com.example.wereadcorrently.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wereadcorrently.R
import com.example.wereadcorrently.databinding.FragmentSetupNotificationsBinding

class SetupNotificationsFragment : Fragment() {

    private var _binding: FragmentSetupNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun isVibrationEnabled() = binding.switchVibration.isChecked
    fun isSoundEnabled() = binding.switchSound.isChecked
    fun isBothEnabled() = binding.switchBoth.isChecked

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}