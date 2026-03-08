package com.example.wereadcorrently.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wereadcorrently.R

class OnboardingPageFragment : Fragment() {

    companion object{
        private const val ARG_PAGE = "page"
        fun newInstance (page : Int) = OnboardingPageFragment().apply { arguments = Bundle().apply { putInt(ARG_PAGE, page) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_onboarding_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val page = arguments?.getInt(ARG_PAGE) ?:0
        val imageView = view.findViewById<ImageView>(R.id.onboardingImage)
        val textView = view.findViewById<TextView>(R.id.onboardingText)

        when(page){
            0 -> {
                imageView.setImageResource(R.drawable.ic_onboarding_1)
                textView.setText(R.string.onboarding_1_text)
            }
            1 -> {
                imageView.setImageResource(R.drawable.ic_onboarding_2)
                textView.setText(R.string.onboarding_2_text)
            }
            2 -> {
                imageView.setImageResource(R.drawable.ic_onboarding_3)
                textView.setText(R.string.onboarding_3_text)
            }
        }
    }
}