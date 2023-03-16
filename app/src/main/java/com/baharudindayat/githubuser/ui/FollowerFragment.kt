package com.baharudindayat.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudindayat.githubuser.adapter.FollowerAdapter
import com.baharudindayat.githubuser.apiservice.FollowersItem
import com.baharudindayat.githubuser.databinding.FragmentFollowerBinding
import com.baharudindayat.githubuser.viewmodel.FollowersViewModel

class FollowerFragment : Fragment() {

    private val followerViewModel by viewModels<FollowersViewModel>()
    private var _binding: FragmentFollowerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFollowerBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        val username = requireActivity().intent.getStringExtra(DetailActivity.USERNAME)

        if (username != null) {
            followerViewModel.getFollowers(username)
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        followerViewModel.followers.observe(viewLifecycleOwner){ followers ->
            setFollowerData(followers)
        }

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun setFollowerData(follower: List<FollowersItem>){
        val adapterFollower = FollowerAdapter(follower)
        binding.rvFollowers.adapter = adapterFollower
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}