package com.test.a2.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.a2.R
import com.test.a2.databinding.FragmentOptionsBinding
import com.test.a2.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : Fragment() {
    private val viewModel by viewModels<AppViewModel>()
    private var _binding: FragmentOptionsBinding? = null
    private val binding: FragmentOptionsBinding
        get() = _binding ?: throw RuntimeException("FragmentOptionsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initObserver()
    }

    private fun initClickListeners() {
        binding.tvRemoveData.setOnClickListener {
            viewModel.clearData()
        }
    }
    private fun initObserver(){
        viewModel.isDataCleared.observe(viewLifecycleOwner){
            if(it)
                findNavController().navigate(R.id.action_optionsFragment_to_loginFragment)
        }
    }
}
