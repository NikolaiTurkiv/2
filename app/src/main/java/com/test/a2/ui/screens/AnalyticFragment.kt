package com.test.a2.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.a2.R
import com.test.a2.data.db.entities.UserEntity
import com.test.a2.databinding.FragmentAnalyticBinding
import com.test.a2.databinding.FragmentLoginBinding
import com.test.a2.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyticFragment : Fragment() {


    private val viewModel by viewModels<AppViewModel>()
    private var _binding: FragmentAnalyticBinding? = null
    private val binding: FragmentAnalyticBinding
        get() = _binding ?: throw RuntimeException("FragmentAnalyticBinding == null")
    private var user: UserEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnalyticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.actualName?.let { viewModel.getUser(it) }
        initObservers()

    }


    private fun initCLickListeners() {
        binding.tvPutData.setOnClickListener {
            if (isEditTextNotEmpty()) {
                viewModel.updateUser(
                    binding.edtDistance.text.toString().toDouble(),
                    binding.edtSitUps.text.toString().toDouble(), user
                )
             } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.field_cannot_be_empty),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }


    }

    private fun initObservers() {
        viewModel.userLD.observe(viewLifecycleOwner) {
            user = it
            binding.pbAnalytics.progress = it.progress
            binding.tvPointsCount.text = it.progress.toString()
            initCLickListeners()
        }
    }

    private fun isEditTextNotEmpty(): Boolean {
        return binding.edtDistance.text.isNotEmpty() && binding.edtSitUps.text.isNotEmpty()
    }

}
