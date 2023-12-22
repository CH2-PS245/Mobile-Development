package com.example.dishdash.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dishdash.data.ViewModelFactory
import com.example.dishdash.data.response.DataUser
import com.example.dishdash.databinding.FragmentProfileBinding
import com.example.dishdash.retrofit.ApiConfig
import com.example.dishdash.ui.Setting.AboutActivity
import com.example.dishdash.ui.Setting.SettingActivity
import com.example.dishdash.ui.Setting.YourGoalsActivity
import com.example.dishdash.ui.Setting.FeedbackActivity
import com.example.dishdash.ui.WelcomeActivity.WelcomeActivity
import com.example.dishdash.ui.viewModel.ProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {
private lateinit var binding : FragmentProfileBinding
private lateinit var call : Call<DataUser>
private val viewModel by viewModels<ProfileViewModel> {
    ViewModelFactory.getInstance(requireContext())
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        viewModel.userName.observe(viewLifecycleOwner) { newName ->
            binding.profileName.text = newName
        }

        viewModel.userEmail.observe(viewLifecycleOwner) { newEmail ->
            binding.textEmail.text = newEmail
        }


        binding.btnSendFeedback.setOnClickListener{
            startActivity(Intent(requireContext(), FeedbackActivity::class.java))
        }

        binding.btnSetting.setOnClickListener{
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }

        binding.myGoals.setOnClickListener {
            startActivity(Intent(requireContext(),YourGoalsActivity::class.java ))
        }
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(requireContext(),AboutActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage("Yakin Ingin Logout?")
            alertDialogBuilder.setPositiveButton("Ya") { dialog, which ->
                viewModel.logout()
                startActivity(Intent(context,WelcomeActivity::class.java))
                requireActivity().finish()
            }
            alertDialogBuilder.setNegativeButton("Tidak"){dialog,which ->
                false
                dialog.dismiss()
            }
            alertDialogBuilder.create().show()
        }
        getUSerById()
        return view
    }

    private fun getUSerById(){
        call = ApiConfig.getApiService().getUserById()
        call.enqueue(object : Callback<DataUser>{
            override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                if (response.isSuccessful){
                    val user = response.body()
                    if (user != null){
                        val photoUrl = user.profile
                        val email = user.email
                        binding.ivProfile.setImageURI(Uri.parse(photoUrl))
                        binding.textEmail.text = email
                    }
                }
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                Log.e("ProfileFragment", t.localizedMessage)
            }

        })
    }

    companion object {

    }
}