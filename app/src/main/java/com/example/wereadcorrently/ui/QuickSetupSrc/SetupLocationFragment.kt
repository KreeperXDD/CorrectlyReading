package com.example.wereadcorrently.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wereadcorrently.R
import com.example.wereadcorrently.databinding.FragmentSetupLocationBinding

class SetupLocationFragment : Fragment() {

    private var _binding: FragmentSetupLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getSelectedLocation(): String {
        return when (binding.chipGroupLocation.checkedChipId) {
            R.id.chipTransport -> "transport"
            R.id.chipHome -> "home"
            R.id.chipDark -> "dark"
            else -> "unknown"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}