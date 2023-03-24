package com.test.a2.ui.screens

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.onesignal.OneSignal
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

    private val args: LoginFragmentArgs by navArgs()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission(view,android.Manifest.permission.POST_NOTIFICATIONS)
        }
        cancelPush()
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

    private fun cancelPush(){
        if(args.push == SplashFragment.Companion.NO)
            OneSignal.disablePush(true)
        else
            OneSignal.disablePush(false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissions.forEach {
            Log.d("PERMISSION",it)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermission(view: View, permission: String){

        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permission
            ) -> {
                requestPermissionLauncher.launch(
                    permission
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    permission
                )
            }
        }
    }
}

private const val MILLISECONDS = 86_400_400L
