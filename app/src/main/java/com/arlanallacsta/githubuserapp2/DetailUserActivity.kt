package com.arlanallacsta.githubuserapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.arlanallacsta.githubuserapp2.ConstValue.EXTRA_USERNAME
import com.arlanallacsta.githubuserapp2.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.tvDetailName.text = it.name
                binding.tvDetailUsername.text = it.login
                binding.tvDetailFollowers.text = "${it.followers}"
                binding.tvDetailFollowing.text = "${it.following}"
                binding.tvDetailRepo.text = "${it.publicRepos}"
                Glide.with(this@DetailUserActivity).load(it.avatarUrl).circleCrop().into(binding.imgDetalUser)

            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this,supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }


}