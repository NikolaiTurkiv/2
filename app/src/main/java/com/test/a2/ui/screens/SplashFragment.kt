package com.test.a2.ui.screens

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.a2.R
import com.test.a2.databinding.FragmentSplashBinding
import com.test.a2.ui.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("FragmentSplashBinding == null")
    private var progress = 0
    private val viewModel by viewModels<SplashViewModel>()


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
        val id = viewModel.id
        val locale = resources.configuration.locale
        val deviceName = getDeviceName()

        if (id.isEmpty()) {
            val uniqueID = UUID.randomUUID().toString()
            viewModel.saveId(uniqueID)
            viewModel.fetchPhoneStatus(deviceName.toString(), locale.toString(), uniqueID)
        } else {
            viewModel.fetchPhoneStatus(deviceName.toString(), locale.toString(), viewModel.id)
        }

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
                viewModel.response.observe(viewLifecycleOwner) { response ->
                    when (response.url) {
                        NO -> {
                            val action =
                                SplashFragmentDirections.actionSplashFragmentToLoginFragment().setPush(NO)

                            findNavController().navigate(action)
                        }
                        NO_PUSH -> {
                            val action =
                                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                                    .setPush(NO_PUSH)
                            findNavController().navigate(action)
                        }
                        else -> {
                            val action =
                                SplashFragmentDirections.actionSplashFragmentToWebViewFragment()
                                    .setSite(
                                        response.url
                                    )
                            findNavController().navigate(action)
                        }
                    }
                }
             }

        }.start()
    }

    private fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return "$manufacturer $model"

    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
    companion object{
        const val NO = "no"
        const val NO_PUSH = "nopush"
    }

}
