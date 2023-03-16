package com.baharudindayat.githubuser.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.baharudindayat.githubuser.R
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.baharudindayat.githubuser.adapter.SectionsPagerAdapter
import com.baharudindayat.githubuser.apiservice.DetailUser
import com.baharudindayat.githubuser.databinding.ActivityDetailBinding
import com.baharudindayat.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val USERNAME = "USERNAME"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(USERNAME)


        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.items.observe(this){ Detail ->
            setDetailData(Detail)
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }


    }

    private fun setDetailData(Detail: DetailUser){
        binding.tvUsername.text = Detail.login
        binding.tvName.text = Detail.name
        binding.tvLocation.text = Detail.location
        binding.tvCompany.text = Detail.company
        binding.tvFollowing.text = Detail.following.toString()
        binding.tvFollower.text = Detail.followers.toString()
        Glide.with(this)
            .load(Detail.avatarUrl)
            .circleCrop()
            .into(binding.ivImageDetail)

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}