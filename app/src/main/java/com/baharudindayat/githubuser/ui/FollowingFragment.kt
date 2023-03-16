package com.baharudindayat.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudindayat.githubuser.adapter.FollowingAdapter
import com.baharudindayat.githubuser.apiservice.FollowingItem
import com.baharudindayat.githubuser.databinding.FragmentFollowingBinding
import com.baharudindayat.githubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private val followingViewModel by viewModels<FollowingViewModel>()

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFollowingBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        val username = requireActivity().intent.getStringExtra(DetailActivity.USERNAME)

        if (username != null) {
            followingViewModel.getFollowing(username)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        followingViewModel.following.observe(viewLifecycleOwner){ following ->
            setFollowingData(following)
        }

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun setFollowingData(following: List<FollowingItem>){
        val adapterFollower = FollowingAdapter(following)
        binding.rvFollowing.adapter = adapterFollower
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}