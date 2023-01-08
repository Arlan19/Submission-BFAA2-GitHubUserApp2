package com.arlanallacsta.githubuserapp2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlanallacsta.githubuserapp2.ConstValue.EXTRA_USERNAME
import com.arlanallacsta.githubuserapp2.databinding.FragmentFollowBinding

class FollowersFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UsersAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)


        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollow.setHasFixedSize(true)
        binding.rvFollow.layoutManager = LinearLayoutManager(activity)
        binding.rvFollow.adapter = adapter

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }
}