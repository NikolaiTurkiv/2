package com.test.a2.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.a2.R
import com.test.a2.databinding.FragmentLoginBinding
import com.test.a2.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<AppViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers()
        viewModel.clearSP()
        clearProgress()
        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.tvStart.setOnClickListener {
            if (isEditTextsNotEmpty())
                viewModel.checkUser(
                    binding.edtName.text.toString(),
                    binding.edtHeight.text.toString().toDouble(),
                    binding.edtWeight.text.toString().toDouble()
                )
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.field_cannot_be_empty),
                    Toast.LENGTH_SHORT
                )
                    .show()
        }
    }

    private fun isEditTextsNotEmpty(): Boolean {
        return (binding.edtHeight.text.isNotEmpty()
                && binding.edtName.text.isNotEmpty()
                && binding.edtWeight.text.isNotEmpty())
    }

    private fun initObservers() {
        viewModel.isUserChecked.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
    }


    private fun clearProgress(){
        val oldTime = viewModel.oldTime

        if(oldTime == 0L){
            viewModel.saveOldTime(System.currentTimeMillis())
            return
        }

        if ( System.currentTimeMillis() - oldTime < MILLISECONDS){
            return
        }else{
            viewModel.saveOldTime(System.currentTimeMillis())
            viewModel.clearProgress()
        }

    }
}

private const val MILLISECONDS = 86L
