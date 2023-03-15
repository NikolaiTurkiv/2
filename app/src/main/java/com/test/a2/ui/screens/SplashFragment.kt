package com.test.a2.ui.screens

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.a2.R
import com.test.a2.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("FragmentSplashBinding == null")
    private var progress = 0

    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTimer()
    }

    private fun startTimer(){
        countDownTimer = object : CountDownTimer(3000,30){
            override fun onTick(p0: Long) {
                progress += 1
                binding.progressBar.progress = progress
                binding.tvProgressStatus.text = progress.toString()
            }

            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
             }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

}
