package com.test.a2.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.a2.R
import com.test.a2.data.db.entities.UserEntity
import com.test.a2.databinding.FragmentMainBinding
import com.test.a2.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {


    private val viewModel by viewModels<AppViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")
    private var user: UserEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers()
        viewModel.getUser(viewModel.actualName.toString())

        Log.d("NAMEUSER",viewModel.actualName.toString())
        initClickListeners()
        initObservers()
     }

    private fun initClickListeners() {
        with(binding) {
            tvTrain.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_trainingFragment)
            }
            tvAnalytics.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_analyticFragment)
            }
            tvStatistic.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_optionsFragment)
            }
        }
    }

    private fun initObservers() {
        viewModel.userLD.observe(viewLifecycleOwner) {
             binding.pbMain.progress = it.progress
            binding.tvProgressCount.text = it.progress.toString()
        }
    }



}

